package com.lj.app.composable.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun DisplayImage(@DrawableRes albumId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = albumId),
        contentDescription = "image",
        modifier = modifier
    )
}
