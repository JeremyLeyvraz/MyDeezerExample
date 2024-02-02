package com.lj.app.composable.audioplayer

import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun VolumeCompactComposable(exoplayer: ExoPlayer) {
    var volume by remember { mutableStateOf(exoplayer.volume) }

    Slider(
        value = volume,
        onValueChange = {
            volume = it
            exoplayer.volume = volume
        },
        steps = 10,
        valueRange = 0f..1f)
}