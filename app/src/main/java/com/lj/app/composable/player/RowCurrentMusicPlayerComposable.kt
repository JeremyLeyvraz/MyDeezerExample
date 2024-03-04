package com.lj.app.composable.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lj.app.R
import com.lj.app.navigation.MUSIC_DESTINATION
import com.lj.app.viewmodel.PlayerViewModel
import com.lj.app.viewmodel.UIEvents

/**
 * A composable function responsible for displaying the current music player in a row layout.
 * @param navController The navigation controller for navigating to the music destination.
 * @param viewModel The view model containing data for the playlist.
 * @param modifier Optional modifier for the current music player.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowCurrentMusicPlayerComposable(navController: NavController, viewModel: PlayerViewModel, modifier: Modifier = Modifier) {

    // Colors for player UI elements
    val playerColor = LocalContext.current.resources.getColor(R.color.purple_700, null)
    val textColor = Color.White
    val iconColor = Color.White

    Card(
        modifier = modifier,
        onClick = {
            navController.navigate(MUSIC_DESTINATION)
        }
    ) {

        Row(modifier = Modifier.background(Color(playerColor))) {

            // Play/Pause button
            IconButton(
                onClick =
                {
                    viewModel.onUiEvents(UIEvents.PlayPause)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                var icon = Icons.Default.PlayArrow
                var description = "Play"
                if(viewModel.isPlaying) {
                    icon = Icons.Default.Pause
                    description = "Pause"
                }
                Icon(
                    icon,
                    contentDescription = description,
                    tint = iconColor,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Display current music information
            viewModel.currentSelectedAudio.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                ) {
                    Column {
//                        Text(
//                            text = it.name,
//                            style = MaterialTheme.typography.labelLarge.copy(
//                                color = textColor,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                            )
//                        )
//                        Text(
//                            text = it.artist,
//                            style = MaterialTheme.typography.labelLarge.copy(
//                                color = textColor,
//                                fontSize = 14.sp,
//                            )
//                        )
                    }
                }
            }

            // Next button
            IconButton(
                onClick = { viewModel.onUiEvents(UIEvents.SeekToNext) },
                modifier = Modifier.padding(8.dp)
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
}