package com.lj.app.composable.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.R
import com.lj.app.composable.audioplayer.AudioPlayerComposable
import com.lj.app.composable.image.DisplayImage
import com.lj.app.viewmodel.MenuViewModel

@Composable
fun MenuComposable(windowSizeClass: WindowSizeClass, viewModel: MenuViewModel) {

    Text(text = "Playlists")

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column {
                Row {
                    DisplayImage(R.drawable.album2)
                }
                Row {
                    AudioPlayerComposable()
                }
            }
        }
        else -> {
            Row {
                Column {
                    DisplayImage(R.drawable.album2)
                }
                Column {
                    AudioPlayerComposable()
                }
            }
        }
    }
}
