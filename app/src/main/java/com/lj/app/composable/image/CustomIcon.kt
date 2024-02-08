package com.lj.app.composable.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image

@Composable
fun CustomIcon(@DrawableRes icon: Int, size: Int) {
    val customIcon: Painter = painterResource(icon)
    Image(
        painter = customIcon,
        contentDescription = "Custom Icon",
        modifier = Modifier.size(size.dp)
    )
}