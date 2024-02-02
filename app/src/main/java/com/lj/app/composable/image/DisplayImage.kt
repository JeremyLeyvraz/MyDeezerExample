package com.lj.app.composable.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.lj.app.R

@Composable
fun DisplayImage(@DrawableRes albumId: Int) {
    Image(
        painter = painterResource(id = albumId),
        contentDescription = "not"
    )
}