package com.lj.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
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

/**
 * Service responsible for managing audio playback using ExoPlayer library in the background.
 * It handles play, pause, skip, and seek actions.
 */
class PlayerService: Service() {
    private var exoPlayer: SimpleExoPlayer? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate() {
        super.onCreate()
        // Initialisez ExoPlayer
        exoPlayer = SimpleExoPlayer.Builder(this).build()

        // Initialize the media session for managing notification buttons
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
                    val responseIntent = Intent()
                    responseIntent.action = ANSWER_METADATA
                    responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                    responseIntent.putExtra("duration", exoPlayer?.duration)
                    responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                    responseIntent.putExtra("isPlaying", exoPlayer!!.isPlaying)
                    sendBroadcast(responseIntent)
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_READY) {

                    currentPositionJob?.cancel()
                    // Start a coroutine to update the current duration at regular intervals
                    currentPositionJob = CoroutineScope(Dispatchers.Main).launch {

                        while (exoPlayer?.currentPosition!! < exoPlayer?.duration!!) {
                            exoPlayer?.let {
                                var responseIntent = Intent()
                                responseIntent.action = UPDATE_PROGRESS
                                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                                responseIntent.putExtra("duration", exoPlayer?.duration ?: 0L)
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

    /**
     * Handles different intents received by the service.
     * It performs actions such as play, pause, skip, seek, and application resume.
     */
    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            REQUEST_PLAY -> {
                val musicId = intent.getStringExtra("musicId")
                val index = getIndex(musicId!!)
                exoPlayer?.seekTo(index, C.TIME_UNSET)
                exoPlayer?.play()
                exoPlayer?.playWhenReady = true

                val responseIntent = Intent()
                responseIntent.action = ANSWER_METADATA
                responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                responseIntent.putExtra("duration", exoPlayer?.duration)
                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                responseIntent.putExtra("isPlaying", true)
                sendBroadcast(responseIntent)
            }
            REQUEST_PAUSE -> {
                exoPlayer?.playWhenReady = !exoPlayer!!.isPlaying
            }
            REQUEST_PREVIOUS -> {
                exoPlayer?.seekToPreviousMediaItem()
                exoPlayer?.play()

                val responseIntent = Intent()
                responseIntent.action = ANSWER_METADATA
                responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                responseIntent.putExtra("duration", exoPlayer?.duration)
                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                responseIntent.putExtra("isPlaying", true)
                sendBroadcast(responseIntent)
            }
            REQUEST_NEXT -> {
                exoPlayer?.seekToNextMediaItem()
                exoPlayer?.play()

                val responseIntent = Intent()
                responseIntent.action = ANSWER_METADATA
                responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                responseIntent.putExtra("duration", exoPlayer?.duration)
                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                responseIntent.putExtra("isPlaying", true)
                sendBroadcast(responseIntent)
            }
            REQUEST_GOTO -> {
                val time = intent.getFloatExtra("progress", 0f)
                val newTime = time * exoPlayer!!.duration / 100.0
                exoPlayer?.seekTo(newTime.toLong())
            }
            REQUEST_METADATA -> {
                val responseIntent = Intent()
                responseIntent.action = ANSWER_METADATA
                responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
                responseIntent.putExtra("duration", exoPlayer?.duration)
                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
                responseIntent.putExtra("isPlaying", exoPlayer!!.isPlaying)
                sendBroadcast(responseIntent)
            }
        }
    }

    /**
     * Retrieves the index of a media item based on its ID.
     */
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

    /**
     * Creates and returns a notification for the service.
     */
    private fun createNotification(): Notification {
        val notificationChannelId = "foreground_service_channel"

        // Create the NotificationChannel
        val channel = NotificationChannel(
            notificationChannelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("My Deezer example")
            .setContentText("Playback service is running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Cleans up resources and releases the ExoPlayer and MediaSession.
     */
    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        mediaSession?.release()
    }

    /**
     * Callback for MediaSession events.
     */
    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            // Resume the current playback
            exoPlayer?.playWhenReady = true
        }

        override fun onPause() {
            // Pause the current playback
            exoPlayer?.playWhenReady = false
        }

        override fun onSkipToNext() {
            // Load and start playing the next track
            exoPlayer?.seekToNextMediaItem()
        }
    }

    /**
     * Constants for action intents handled by the service.
     */
    companion object {

        const val REQUEST_PLAY = "REQUEST_PLAY"
        const val REQUEST_PAUSE = "REQUEST_PAUSE"
        const val REQUEST_NEXT = "REQUEST_NEXT"
        const val REQUEST_PREVIOUS = "REQUEST_PREVIOUS"
        const val REQUEST_GOTO = "REQUEST_GOTO"

        const val STATE_PLAY = "STATE_PLAY"
        const val STATE_PAUSE = "STATE_PAUSE"
        const val STATE_REPRISE = "STATE_REPRISE"

        const val REQUEST_METADATA = "REQUEST_METADATA"
        const val ANSWER_METADATA = "ANSWER_METADATA"

        const val UPDATE_PROGRESS = "UPDATE_PROGRESS"
    }
}
