package com.lj.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.lj.app.MainActivity
import com.lj.app.R
import com.lj.app.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerService: Service() {
    private var exoPlayer: SimpleExoPlayer? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate() {
        super.onCreate()
        // Initialisez ExoPlayer
        exoPlayer = SimpleExoPlayer.Builder(this).build()

        // Initialisez la session multimédia pour la gestion des boutons de notification
        mediaSession = MediaSessionCompat(this, "AudioService")
        mediaSession?.setCallback(MediaSessionCallback())
        mediaSession?.isActive = true

        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer?.prepare()

        val playlist = Playlist()
        playlist.getPlayList().forEach {
            val path = "android.resource://" + packageName + "/" + it.music
            val mediaItem = MediaItem.Builder()
                .setMediaId(it.name)
                .setUri(Uri.parse(path))
                .build()
            exoPlayer?.addMediaItem(mediaItem)
        }

        exoPlayer?.addListener(object : Player.Listener {

            private var currentPositionJob: Job? = null

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                mediaItem?.let {
                    var responseIntent = Intent()
                    responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
                    responseIntent.putExtra("musicId", mediaItem.mediaId)
                    sendBroadcast(responseIntent)

                    responseIntent = Intent()
                    responseIntent.action = ACTION_DURATION
                    responseIntent.putExtra("duration", exoPlayer?.duration)
                    sendBroadcast(responseIntent)
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_READY) {
                    var responseIntent = Intent()
                    responseIntent.action = ACTION_DURATION
                    responseIntent.putExtra("duration", exoPlayer?.duration)
                    sendBroadcast(responseIntent)

                    currentPositionJob?.cancel()
                    // Lancer une coroutine pour mettre à jour la durée courante à intervalles réguliers
                    currentPositionJob = CoroutineScope(Dispatchers.Main).launch {

                        while (exoPlayer?.currentPosition!! < exoPlayer?.duration!!) {
                            exoPlayer?.let {
                                responseIntent = Intent()
                                responseIntent.action = ACTION_CURRENT_POSITION
                                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                                sendBroadcast(responseIntent)
                            }
                            delay(1000)
                        }
                    }
                }
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
        startForeground(1, createNotification())
        return START_REDELIVER_INTENT
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY -> {
                val musicId = intent.getStringExtra("musicId")
                // Mettez en pause la lecture actuelle
                val index = getIndex(musicId!!)
                exoPlayer?.seekTo(index, C.TIME_UNSET)
                exoPlayer?.play()
                exoPlayer?.playWhenReady = true
            }
            ACTION_PAUSE -> {
                if(exoPlayer!!.isPlaying) {
                    //exoPlayer!!.pause()
                    exoPlayer?.playWhenReady = false
                } else {
                    //exoPlayer!!.play()
                    exoPlayer?.playWhenReady = true
                }
            }
            ACTION_STOP -> {
                // Arrêtez la lecture actuelle
                exoPlayer?.stop()
                stopSelf()
            }
            ACTION_NEXT -> {
                // Chargez et commencez la lecture de la piste suivante
                exoPlayer?.seekToNextMediaItem()
                exoPlayer?.play()
            }
            ACTION_PREVIOUS -> {
                // Chargez et commencez la lecture de la piste suivante
                exoPlayer?.seekToPreviousMediaItem()
                exoPlayer?.play()
            }
            ACTION_GOTO -> {
                // Chargez et commencez la lecture de la piste suivante
                val time = intent.getFloatExtra("progress", 0f)

                val newTime = time * exoPlayer!!.duration / 100.0



                exoPlayer?.seekTo(newTime.toLong())
            }
//            ACTION_CURRENT_MUSIC_REQUEST -> {
//                val responseIntent = Intent()
//                responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
//                    responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
//                sendBroadcast(responseIntent)
//            }
            ACTION_APPLICATION_RESUME -> {
                // Send current music ID
                var responseIntent = Intent()
                if (exoPlayer!!.isPlaying || exoPlayer!!.currentPosition.toInt() != 0) {

                    responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
                    responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                    sendBroadcast(responseIntent)

                    responseIntent = Intent()
                    responseIntent.action = ACTION_CURRENT_POSITION
                    responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                    sendBroadcast(responseIntent)

                    responseIntent = Intent()
                    responseIntent.action = ACTION_DURATION
                    responseIntent.putExtra("duration", exoPlayer?.duration)
                    sendBroadcast(responseIntent)

                    responseIntent = Intent()
                    responseIntent.action = ACTION_APPLICATION_RESUME_RESULT
                    responseIntent.putExtra("isPlaying", exoPlayer!!.isPlaying)
                    sendBroadcast(responseIntent)
                }
            }
        }
    }

    private fun getIndex(musidId: String): Int {
        val count = exoPlayer?.mediaItemCount;

        for (i in 0..count!!) {
            val media = exoPlayer?.getMediaItemAt(i)
            if(media?.mediaId == musidId) {
                return i
            }
        }

        return -1
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "foreground_service_channel"

        // Create a notification channel if the Android version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val channel = NotificationChannel(
                notificationChannelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Foreground Service Example")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        mediaSession?.release()
    }

    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            // Mettez en pause la lecture actuelle
            exoPlayer?.playWhenReady = true
        }

        override fun onPause() {
            // Mettez en pause la lecture actuelle
            exoPlayer?.playWhenReady = false
        }

        override fun onStop() {
            // Arrêtez la lecture actuelle
            exoPlayer?.stop()
            stopSelf()
        }

        override fun onSkipToNext() {
            // Chargez et commencez la lecture de la piste suivante
            exoPlayer?.seekToNextMediaItem()
        }
    }

    companion object {
        const val ACTION_GOTO = "ACTION_GOTO"
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
        const val ACTION_CURRENT_MUSIC_RESULT = "ACTION_CURRENT_MUSIC_RESULT"
        const val ACTION_DURATION = "ACTION_DURATION"
        const val ACTION_CURRENT_POSITION = "ACTION_CURRENT_POSITION"


        const val ACTION_APPLICATION_RESUME = "ACTION_APPLICATION_RESUME"
        const val ACTION_APPLICATION_RESUME_RESULT = "ACTION_APPLICATION_RESUME_RESULT"
    }
}