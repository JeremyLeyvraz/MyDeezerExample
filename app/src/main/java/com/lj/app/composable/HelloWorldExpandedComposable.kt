package com.lj.app.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.composable.audioplayer.AudioPlayerComposable
import androidx.compose.ui.res.painterResource
import com.lj.app.R
import com.lj.app.composable.image.DisplayImage

@Composable
fun HelloWorldExpandedComposable(windowSizeClass: WindowSizeClass, viewModel: HelloViewModel) {
    Row {
        Column {
            DisplayImage(R.drawable.album1)
        }
        Column {
            AudioPlayerComposable()
        }
    }
}




