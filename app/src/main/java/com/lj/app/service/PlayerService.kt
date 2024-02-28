package com.lj.app.service

import android.content.Intent
import android.os.Build
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.lj.app.notification.CustomNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Service responsible for managing audio playback using ExoPlayer library in the background.
 * It handles play, pause, skip, and seek actions.
 */
@AndroidEntryPoint
class PlayerService: MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: CustomNotificationManager

    // Create your player and media session in the onCreate lifecycle event
    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSession = mediaSession,
            mediaSessionService = this
        )
        return super.onStartCommand(intent, flags, startId)
    }

    // The user dismissed the app from the recent tasks
    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player!!
        if (!player.playWhenReady || player.mediaItemCount == 0) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        super.onDestroy()
        mediaSession.apply {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }

//    private var exoPlayer: SimpleExoPlayer? = null
//    private var mediaSession: MediaSessionCompat? = null

//    override fun onCreate() {
//        super.onCreate()
//        // Initialisez ExoPlayer
//        exoPlayer = SimpleExoPlayer.Builder(this).build()
//
//        // Initialize the media session for managing notification buttons
//        mediaSession = MediaSessionCompat(this, "AudioService")
//        mediaSession?.setCallback(MediaSessionCallback())
//        mediaSession?.isActive = true
//
//        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
//        exoPlayer?.prepare()
//
//        val playlist = Playlist()
//        playlist.getPlayList().forEach {
//            val path = "android.resource://" + packageName + "/" + it.music
//            val mediaItem = MediaItem.Builder()
//                .setMediaId(it.name)
//                .setUri(Uri.parse(path))
//                .build()
//            exoPlayer?.addMediaItem(mediaItem)
//        }
//
//        exoPlayer?.addListener(object : Player.Listener {
//
//            private var currentPositionJob: Job? = null
//
//            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
//                super.onMediaItemTransition(mediaItem, reason)
//                mediaItem?.let {
//                    var responseIntent = Intent()
//                    responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
//                    responseIntent.putExtra("musicId", mediaItem.mediaId)
//                    sendBroadcast(responseIntent)
//
//                    responseIntent = Intent()
//                    responseIntent.action = ACTION_DURATION
//                    responseIntent.putExtra("duration", exoPlayer?.duration)
//                    sendBroadcast(responseIntent)
//                }
//            }
//
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                super.onPlayerStateChanged(playWhenReady, playbackState)
//                if (playbackState == Player.STATE_READY) {
//                    var responseIntent = Intent()
//                    responseIntent.action = ACTION_DURATION
//                    responseIntent.putExtra("duration", exoPlayer?.duration)
//                    sendBroadcast(responseIntent)
//
//                    currentPositionJob?.cancel()
//                    // Start a coroutine to update the current duration at regular intervals
//                    currentPositionJob = CoroutineScope(Dispatchers.Main).launch {
//
//                        while (exoPlayer?.currentPosition!! < exoPlayer?.duration!!) {
//                            exoPlayer?.let {
//                                responseIntent = Intent()
//                                responseIntent.action = ACTION_CURRENT_POSITION
//                                responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
//                                sendBroadcast(responseIntent)
//                            }
//                            delay(1000)
//                        }
//                    }
//                }
//            }
//        })
//    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        handleIntent(intent)
//        startForeground(1, createNotification())
//        return START_REDELIVER_INTENT
//    }

    /**
     * Handles different intents received by the service.
     * It performs actions such as play, pause, skip, seek, and application resume.
     */
//    private fun handleIntent(intent: Intent?) {
//        when (intent?.action) {
//            ACTION_PLAY -> {
//                val musicId = intent.getStringExtra("musicId")
//                val index = getIndex(musicId!!)
//                exoPlayer?.seekTo(index, C.TIME_UNSET)
//                exoPlayer?.play()
//                exoPlayer?.playWhenReady = true
//            }
//            ACTION_PAUSE -> {
//                exoPlayer?.playWhenReady = !exoPlayer!!.isPlaying
//            }
//            // Load and start playing the next track
//            ACTION_NEXT -> {
//                exoPlayer?.seekToNextMediaItem()
//                exoPlayer?.play()
//            }
//            // Load and start playing the previous track
//            ACTION_PREVIOUS -> {
//                exoPlayer?.seekToPreviousMediaItem()
//                exoPlayer?.play()
//            }
//            // Load and start playing the track at a specific progress
//            ACTION_GOTO -> {
//                val time = intent.getFloatExtra("progress", 0f)
//                val newTime = time * exoPlayer!!.duration / 100.0
//                exoPlayer?.seekTo(newTime.toLong())
//            }
//            // Send current music ID and playback status
//            ACTION_APPLICATION_RESUME -> {
//                var responseIntent = Intent()
//                if (exoPlayer!!.isPlaying || exoPlayer!!.currentPosition.toInt() != 0) {
//
//                    responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
//                    responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)
//                    sendBroadcast(responseIntent)
//
//                    responseIntent = Intent()
//                    responseIntent.action = ACTION_CURRENT_POSITION
//                    responseIntent.putExtra("currentPosition", exoPlayer?.currentPosition ?: 0L)
//                    sendBroadcast(responseIntent)
//
//                    responseIntent = Intent()
//                    responseIntent.action = ACTION_DURATION
//                    responseIntent.putExtra("duration", exoPlayer?.duration)
//                    sendBroadcast(responseIntent)
//
//                    responseIntent = Intent()
//                    responseIntent.action = ACTION_APPLICATION_RESUME_RESULT
//                    responseIntent.putExtra("isPlaying", exoPlayer!!.isPlaying)
//                    sendBroadcast(responseIntent)
//                }
//            }
//        }
//    }
//
//    /**
//     * Retrieves the index of a media item based on its ID.
//     */
//    private fun getIndex(musidId: String): Int {
//        val count = exoPlayer?.mediaItemCount;
//
//        for (i in 0..count!!) {
//            val media = exoPlayer?.getMediaItemAt(i)
//            if(media?.mediaId == musidId) {
//                return i
//            }
//        }
//
//        return -1
//    }
//
//    /**
//     * Creates and returns a notification for the service.
//     */
//    @SuppressLint("RemoteViewLayout")
//    private fun createNotification(): Notification {
//        val notificationChannelId = "foreground_service_channel"
//
//        // Create the NotificationChannel
//        val channel = NotificationChannel(
//            notificationChannelId,
//            "Foreground Service Channel",
//            NotificationManager.IMPORTANCE_LOW
//        )
//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//
//        // Create the notification layout using RemoteViews
//        val remoteViews = RemoteViews(this.packageName, R.layout.player)
//
//
//
//        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            notificationIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//        return NotificationCompat.Builder(this, notificationChannelId)
//            .setContentTitle("My Deezer example")
//            .setContentText("Playback service is running...")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentIntent(pendingIntent)
//            .setCustomContentView(remoteViews)
//            .build()
//    }

//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }

    /**
     * Cleans up resources and releases the ExoPlayer and MediaSession.
     */
//    override fun onDestroy() {
//        super.onDestroy()
//        exoPlayer?.release()
//        mediaSession?.release()
//    }

    /**
     * Callback for MediaSession events.
     */
//    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
//        override fun onPlay() {
//            // Resume the current playback
//            exoPlayer?.playWhenReady = true
//        }
//
//        override fun onPause() {
//            // Pause the current playback
//            exoPlayer?.playWhenReady = false
//        }
//
//        override fun onSkipToNext() {
//            // Load and start playing the next track
//            exoPlayer?.seekToNextMediaItem()
//        }
//    }

    /**
     * Constants for action intents handled by the service.
     */
    companion object {

        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"

        const val ACTION_GOTO = "ACTION_GOTO"

        const val ACTION_DURATION = "ACTION_DURATION"
        const val ACTION_CURRENT_POSITION = "ACTION_CURRENT_POSITION"

        const val ACTION_CURRENT_MUSIC_RESULT = "ACTION_CURRENT_MUSIC_RESULT"

        const val ACTION_APPLICATION_RESUME = "ACTION_APPLICATION_RESUME"
        const val ACTION_APPLICATION_RESUME_RESULT = "ACTION_APPLICATION_RESUME_RESULT"
    }
}