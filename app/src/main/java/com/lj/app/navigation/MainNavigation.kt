package com.lj.app.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lj.app.composable.menu.MenuComposable
import com.lj.app.composable.music.MusicComposable
import com.lj.app.composable.playlist.PlaylistComposable
import com.lj.app.viewmodel.MenuViewModel
import com.lj.app.viewmodel.MusicViewModel
import com.lj.app.viewmodel.PlaylistViewModel

const val HOME_DESTINATION = "home"
const val PLAYLIST_DESTINATION = "playlist"
const val MUSIC_DESTINATION = "music"

/**
 * Define the navigation through the application.
 */
@Composable
fun MainNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PLAYLIST_DESTINATION) {
        composable(HOME_DESTINATION)
        {
            val viewModel = hiltViewModel<MenuViewModel>()
//            viewModel.init(LocalContext.current)
            MenuComposable(windowSizeClass, viewModel)
        }
        composable(PLAYLIST_DESTINATION)
        {
            val viewModel = hiltViewModel<PlaylistViewModel>()
//            viewModel.init(LocalContext.current)
            PlaylistComposable(windowSizeClass, navController, viewModel)
        }
        composable("$MUSIC_DESTINATION/{playlistId}/{musicId}")
        {
            val playlistId = it.arguments?.getString("playlistId")
            val musicId = it.arguments?.getString("musicId")
            val viewModel = hiltViewModel<MusicViewModel>()
            playlistId?.let {
                musicId?.let {
                    viewModel.init(LocalContext.current, playlistId, musicId)
                    MusicComposable(windowSizeClass, viewModel)
                }
            }
        }
    }
}
