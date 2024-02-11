package com.lj.app.composable.playlist

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lj.app.viewmodel.PlaylistViewModel

@Composable
fun PlaylistComposable(windowSizeClass: WindowSizeClass, navController: NavController, viewModel: PlaylistViewModel) {

    when (windowSizeClass.widthSizeClass) {
        // Vue portrait téléphone
        WindowWidthSizeClass.Compact -> {
            PlaylistCompactComposable(navController, viewModel)
        }
        // Vue portrait tablette
        WindowWidthSizeClass.Medium -> {
            PlaylistMediumComposable(navController, viewModel)
        }
        // Vue paysage téléphone et tablette
        WindowWidthSizeClass.Expanded -> {
            PlaylistExpandedComposable(navController, viewModel)
        }
    }
}
