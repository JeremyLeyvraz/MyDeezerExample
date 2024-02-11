package com.lj.app.composable.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lj.app.R
import com.lj.app.composable.image.DisplayImage
import com.lj.app.composable.player.RowMainCommandComposable
import com.lj.app.converter.formatLongToMinutesSeconds
import com.lj.app.viewmodel.PlaylistViewModel

/**
 * A composable function responsible for displaying the music in a compact layout.
 * @param viewModel The view model containing data for the playlist.
 */
@Composable
fun MusicCompactComposable(viewModel: PlaylistViewModel) {

    // Colors for UI elements
    val backgroundColor = LocalContext.current.resources.getColor(R.color.purple_700, null)
    val textColor = Color.White

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(backgroundColor))) {
        viewModel.currentMusic.value?.let {

            // Display image
            Surface(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)) {
                DisplayImage(resourceId = it.cover,
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f))
            }

            Spacer(modifier = Modifier.weight(.5f))

            // Music title
            Text(
                text = it.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 32.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Artist name
            Text(
                text = it.artist,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 24.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1f))

        }

        // Current position and remaining time
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(start = 16.dp, end = 16.dp)) {

            Text(
                text = formatLongToMinutesSeconds(viewModel.currentPosition.value),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = formatLongToMinutesSeconds(viewModel.duration.value - viewModel.currentPosition.value),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Seekbar
        Slider(
            value = viewModel.progress.value,
            onValueChange = {
                viewModel.goTo(it)
            },
            valueRange = 0f..100f,
            steps = 100,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Playback controls (previous, play/pause, next)
        RowMainCommandComposable(viewModel, Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.weight(.5f))
    }
}
