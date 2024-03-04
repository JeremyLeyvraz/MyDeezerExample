package com.lj.app.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lj.app.composable.music.MusicComposable
import com.lj.app.composable.playlist.PlaylistComposable
import com.lj.app.viewmodel.PlayerViewModel

const val PLAYLIST_DESTINATION = "playlist"
const val MUSIC_DESTINATION = "music"

/**
 * Define the navigation through the application.
 */
@Composable
fun MainNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<PlayerViewModel>()
    viewModel.mContext = LocalContext.current
    NavHost(navController = navController, startDestination = PLAYLIST_DESTINATION) {
        // Define a composable for the playlist destination
        composable(PLAYLIST_DESTINATION)
        {
            PlaylistComposable(windowSizeClass, navController, viewModel)
        }
        // Define a composable for the music destination
        composable(MUSIC_DESTINATION)
        {
            MusicComposable(windowSizeClass, viewModel)
        }
    }
}
