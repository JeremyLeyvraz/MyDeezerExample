package com.lj.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.lj.app.R
import com.lj.app.composable.HelloViewModel

private const val HOME_DESTINATION = "home"

/**
 * Define the navigation through the application.
 */
@Composable
fun MainNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_DESTINATION) {
        composable(HOME_DESTINATION)
        {
            val viewModel = hiltViewModel<HelloViewModel>()
            viewModel.init()
            //HelloWorldComposable(viewModel)

            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    CompactUI()
                }
                else -> {
                    ExpandedUI()
                }
            }
        }
    }
}







@Composable
fun CompactUI() {
    Text(text = "Compact")
    AudioPlayer()
}

@Composable
fun ExpandedUI() {
    Text(text = "Expanded")
    AudioPlayer()
}

@Composable
fun AudioPlayer() {
    var exoPlayer: ExoPlayer? by remember { mutableStateOf(null) }

    val context = LocalContext.current

    DisposableEffect(context) {
        exoPlayer = SimpleExoPlayer.Builder(context).build()
        onDispose {
            exoPlayer?.release()
        }
    }

    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            isPlaying = !isPlaying
            if (isPlaying) {
                exoPlayer?.setMediaSource(
                    ProgressiveMediaSource.Factory(
                        DefaultDataSourceFactory(context, "YourAppName")
                    ).createMediaSource(MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.hollow}")))
                exoPlayer?.prepare()
                exoPlayer?.playWhenReady = true
            } else {
                exoPlayer?.playWhenReady = false
            }
        }) {
            Icon(
                if (isPlaying) Icons.Default.Refresh else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        IconButton(onClick = {
            exoPlayer?.stop()
            isPlaying = false
        }) {
            Icon(Icons.Default.Clear, contentDescription = null, tint = Color.Black)
        }
    }
}
