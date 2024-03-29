package com.lj.app.composable.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.lj.app.R
import com.lj.app.composable.image.DisplayImage
import com.lj.app.model.Music

/**
 * A composable function responsible for displaying a single music item.
 * @param music The music item to display.
 * @param isPlaying Indicates whether the music is currently playing.
 */
@Composable
fun MusicItemComposable(music: Music, isPlaying: Boolean) {

    // Colors for UI elements
    val backgroundColor = Color.Black
    val textColor = Color.White

    Row(modifier = Modifier.background(backgroundColor)) {

        // Display music cover image
        DisplayImage(resourceId = music.cover, modifier = Modifier
            .width(80.dp)
            .height(80.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .align(Alignment.CenterVertically)
            .weight(1.0f)) {

            // Music title
            Text(
                text = music.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = textColor,
                    fontSize = 20.sp,
                )
            )
            // Artist name
            Text(
                text = music.artist,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = textColor,
                    fontSize = 14.sp,
                )
            )
        }

        // Display playing indicator if the music is currently playing
        if(isPlaying) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        add(ImageDecoderDecoder.Factory())
                    }
                    .build()

                Image(
                    painter = rememberAsyncImagePainter(R.drawable.playing, imageLoader),
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(end = 10.dp)
                )
            }
        }
    }
}
