package com.lj.app.service

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.lj.app.R
import com.lj.app.model.Playlist

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

        exoPlayer?.setMediaSource(
            ProgressiveMediaSource.Factory(
                DefaultDataSourceFactory(this.applicationContext, "YourAppName")
            ).createMediaSource(MediaItem.fromUri("android.resource://${this.applicationContext.packageName}/${R.raw.hollow}")))
        exoPlayer?.prepare()
        exoPlayer?.volume = .5F

//        exoPlayer?.addListener(object : Player.EventListener {
//            // Ajoutez des écouteurs d'événements ExoPlayer si nécessaire
//        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY -> {
                // Mettez en pause la lecture actuelle
                exoPlayer?.playWhenReady = true
            }
            ACTION_PAUSE -> {
                // Mettez en pause la lecture actuelle
                exoPlayer?.playWhenReady = false
            }
            ACTION_STOP -> {
                // Arrêtez la lecture actuelle
                exoPlayer?.stop()
                stopSelf()
            }
            ACTION_NEXT -> {
                // Chargez et commencez la lecture de la piste suivante
                exoPlayer?.seekToNextMediaItem()
            }
            ACTION_LOAD_PLAYLIST -> {
                // Chargez et commencez la lecture de la piste suivante
                val playlist = Playlist()
                playlist.getPlayList().forEach {
                    val path = "android.resource://" + packageName + "/" + it.music
                    val mediaItem = MediaItem.Builder()
                        .setMediaId(it.guid)
                        .setUri(Uri.parse(path))
                        .build()
                    exoPlayer?.addMediaItem(mediaItem)
                }
            }
            ACTION_CHANGE_MUSIC -> {
                // Chargez et commencez la lecture de la piste suivante
                val playlist = Playlist()
                playlist.getPlayList().forEach {
                    val path = "android.resource://" + packageName + "/" + it.music
                    val mediaItem = MediaItem.Builder()
                        .setMediaId(it.guid)
                        .setUri(Uri.parse(path))
                        .build()
                    exoPlayer?.addMediaItem(mediaItem)
                }
            }
        }
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
        const val ACTION_PLAY = "com.example.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.ACTION_PAUSE"
        const val ACTION_STOP = "com.example.ACTION_STOP"
        const val ACTION_NEXT = "com.example.ACTION_NEXT"
        const val ACTION_LOAD_PLAYLIST = "com.example.ACTION_LOAD_PLAYLIST"
        const val ACTION_CHANGE_MUSIC = "com.example.ACTION_CHANGE_MUSIC"
    }
}