package com.lj.app.service

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

//        exoPlayer?.setMediaSource(
//            ProgressiveMediaSource.Factory(
//                DefaultDataSourceFactory(this.applicationContext, "YourAppName")
//            ).createMediaSource(MediaItem.fromUri("android.resource://${this.applicationContext.packageName}/${R.raw.hollow}")))
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer?.prepare()
        //exoPlayer?.volume = 1.0F


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
            override fun onPositionDiscontinuity(reason: Int) {
                super.onPositionDiscontinuity(reason)
                // La position a changé, ce qui peut indiquer le changement de la musique en cours de lecture
                // Vous pouvez vérifier le nouvel indice de fenêtre et traiter en conséquence
                val windowIndex = exoPlayer!!.currentWindowIndex
                // Faites quelque chose avec le nouvel indice de fenêtre
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                // MediaItem a changé, ce qui signifie que la piste de musique a changé
                // Vous pouvez récupérer des informations sur le nouveau MediaItem, par exemple, son URI, son titre, etc.
                mediaItem?.let {
                    val responseIntent = Intent()
                    responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
                    responseIntent.putExtra("musicId", mediaItem.mediaId)

                    sendBroadcast(responseIntent)
                }
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
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
            ACTION_CURRENT_MUSIC_REQUEST -> {
                val responseIntent = Intent()
                responseIntent.action = ACTION_CURRENT_MUSIC_RESULT
                    responseIntent.putExtra("musicId", exoPlayer?.currentMediaItem?.mediaId)

                sendBroadcast(responseIntent)
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
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_CURRENT_MUSIC_REQUEST = "ACTION_CURRENT_MUSIC_REQUEST"
        const val ACTION_CURRENT_MUSIC_RESULT = "ACTION_CURRENT_MUSIC_RESULT"
    }
}