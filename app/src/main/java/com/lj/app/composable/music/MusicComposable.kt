package com.lj.app.composable.music

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.viewmodel.PlaylistViewModel

@Composable
fun MusicComposable (windowSizeClass: WindowSizeClass, viewModel: PlaylistViewModel) {

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MusicCompactComposable(viewModel)
        }
        WindowWidthSizeClass.Medium -> {
            MusicMediumComposable(viewModel)
        }
        WindowWidthSizeClass.Expanded -> {
            MusicExpandedComposable(viewModel)
        }
    }
}