package com.lj.app.composable.music

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.lj.app.manager.MusicManager

@Composable
fun MusicComposable(windowSizeClass: WindowSizeClass, viewModel: MusicManager) {

    Column {
        Row {
            Text(text = viewModel.playlistId)
        }
        Row {
            Text(text = viewModel.musicId)
        }
        Row {
            IconButton(onClick = {
                viewModel.play()
            }) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        Row {
            IconButton(onClick = {
                viewModel.pause()
            }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        Row {
            IconButton(onClick = {
                viewModel.stop()
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }

    /**
     * TODO:
     * A partir de l'ID envoyé par le clic depuis la vue Playlist,
     * récupérer toutes les infos et les afficher
     *
     */
}