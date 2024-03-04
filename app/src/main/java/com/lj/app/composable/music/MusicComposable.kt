package com.lj.app.composable.music

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.viewmodel.PlayerViewModel
import com.lj.app.viewmodel.PlaylistViewModel

/**
 * A composable function responsible for displaying music based on the window size class.
 * @param windowSizeClass The window size class indicating the current screen size.
 * @param viewModel The view model containing data for the music playlist.
 */
@Composable
fun MusicComposable (windowSizeClass: WindowSizeClass, viewModel: PlayerViewModel) {

    when (windowSizeClass.widthSizeClass) {
        // Compact view for portrait mode on phones
        WindowWidthSizeClass.Compact -> {
            MusicCompactComposable(viewModel)
        }
        // Medium view for portrait mode on tablets
        WindowWidthSizeClass.Medium -> {
            //MusicMediumComposable(viewModel)
        }
        // Expanded view for landscape mode on phones and tablets
        WindowWidthSizeClass.Expanded -> {
            //MusicExpandedComposable(viewModel)
        }
    }
}