package com.lj.app.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lj.app.R
import com.lj.app.composable.audioplayer.AudioPlayerComposable
import com.lj.app.composable.audioplayer.VolumeCompactComposable
import com.lj.app.composable.image.DisplayImage

/**
 * Display a 'Hello' message.
 */
@Composable
fun HelloWorldCompactComposable(windowSizeClass: WindowSizeClass, viewModel: HelloViewModel) {
    Column {
        Row {
            DisplayImage(R.drawable.album2)
        }
        Row {
            AudioPlayerComposable()
        }
        Row {
            //viewModel.exoPlayer?.let { VolumeCompactComposable(it) }
        }
        Row {
            //Text("toto")
        }
    }
}