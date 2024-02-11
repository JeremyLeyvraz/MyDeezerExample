package com.lj.app.composable.playlist

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lj.app.viewmodel.PlaylistViewModel

/**
 * A composable function responsible for displaying the playlist based on the window size class.
 * @param windowSizeClass The window size class indicating the current screen size.
 * @param navController The navigation controller for navigating between composables.
 * @param viewModel The view model containing data for the playlist.
 */
@Composable
fun PlaylistComposable(windowSizeClass: WindowSizeClass, navController: NavController, viewModel: PlaylistViewModel) {

    when (windowSizeClass.widthSizeClass) {
        // Compact view for portrait mode on phones
        WindowWidthSizeClass.Compact -> {
            PlaylistCompactComposable(navController, viewModel)
        }
        // Medium view for portrait mode on tablets
        WindowWidthSizeClass.Medium -> {
            PlaylistMediumComposable(navController, viewModel)
        }
        // Expanded view for landscape mode on phones and tablets
        WindowWidthSizeClass.Expanded -> {
            PlaylistExpandedComposable(navController, viewModel)
        }
    }
}
