package com.lj.app.composable.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lj.app.viewmodel.PlaylistViewModel

/**
 * A composable function responsible for displaying the main playback control buttons in a row layout.
 * @param viewModel The view model containing data for the playlist.
 * @param modifier Optional modifier for the row layout.
 */
@Composable
fun RowMainCommandComposable(viewModel: PlaylistViewModel, modifier: Modifier = Modifier) {

    // Color for icon tint
    val iconColor = Color.White

    Row(modifier = modifier) {

        // Previous button
        IconButton(
            onClick = { viewModel.previous() },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                tint = iconColor,
                modifier = Modifier.size(48.dp)
            )
        }

        // Play/Pause button
        IconButton(
            onClick =
            {
                viewModel.pause()
            },
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .align(Alignment.CenterVertically)
        ) {
            var icon = Icons.Default.Pause
            var description = "Pause"
            if (viewModel.isPause.value) {
                icon = Icons.Default.PlayArrow
                description = "Play"
            }
            Icon(
                icon,
                contentDescription = description,
                tint = iconColor,
                modifier = Modifier.size(72.dp)
            )
        }

        // Next button
        IconButton(
            onClick = { viewModel.next() },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Next",
                tint = iconColor,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
