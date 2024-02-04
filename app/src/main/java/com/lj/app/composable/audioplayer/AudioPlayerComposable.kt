package com.lj.app.composable.audioplayer

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.lj.app.R
import com.lj.app.service.PlayerService

@Composable
fun AudioPlayerComposable() {

    val context = LocalContext.current

//    var exoPlayer: ExoPlayer? by remember { mutableStateOf(null) }
//
//    DisposableEffect(context) {
//        exoPlayer = SimpleExoPlayer.Builder(context).build()
//        onDispose {
//            exoPlayer?.release()
//        }
//    }

    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            isPlaying = !isPlaying
//            if (isPlaying) {
//                exoPlayer?.setMediaSource(
//                    ProgressiveMediaSource.Factory(
//                        DefaultDataSourceFactory(context, "YourAppName")
//                    ).createMediaSource(MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.hollow}")))
//                exoPlayer?.prepare()
//                exoPlayer?.volume = .5F
//                exoPlayer?.playWhenReady = true
//            } else {
//                exoPlayer?.playWhenReady = false
//            }
            if(isPlaying) {
                val stopIntent = Intent(context, PlayerService::class.java).setAction(PlayerService.ACTION_PAUSE)
                context.startService(stopIntent)
            } else {
                val stopIntent = Intent(context, PlayerService::class.java).setAction(PlayerService.ACTION_PLAY)
                context.startService(stopIntent)
            }
        }) {
            Icon(
                if (isPlaying) Icons.Default.Refresh else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // STOP
        IconButton(onClick = {
//            exoPlayer?.stop()
            isPlaying = false
            val stopIntent = Intent(context, PlayerService::class.java).setAction(PlayerService.ACTION_STOP)
            context.startService(stopIntent)
        }) {
            Icon(Icons.Default.Clear, contentDescription = null, tint = Color.Black)
        }

//        var volume by remember { mutableStateOf(0.5f) }
//
//        volume?.let {
//            Slider(
//                value = it,
//                onValueChange = {
////                    volume = it
////                    exoPlayer?.volume = volume as Float
//                },
//                steps = 10,
//                valueRange = 0f..1f
//            )
//        }
    }
}