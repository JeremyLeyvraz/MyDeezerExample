package com.lj.app.composable.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

/**
 * Displays an image identified by the provided resource ID.
 * @param resourceId The resource ID of the image to be displayed.
 * @param modifier Optional modifier for the image.
 */
@Composable
fun DisplayImage(@DrawableRes resourceId: Int, modifier: Modifier = Modifier) {

    /**
     * Crash si resourceId n'existe pas
     * A faire: mettre une resource par défaut + vérifier l'existance de la resource hors composable + mettre défaut si existe pas
     */

    Image(
        painter = painterResource(id = resourceId),
        contentDescription = "image"
        modifier = modifier
    )
}
