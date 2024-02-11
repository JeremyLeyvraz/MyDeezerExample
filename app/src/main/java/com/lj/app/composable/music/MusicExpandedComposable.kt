package com.lj.app.composable.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lj.app.composable.image.DisplayImage
import com.lj.app.converter.formatLongToMinutesSeconds
import com.lj.app.viewmodel.PlaylistViewModel

@Composable
fun MusicExpandedComposable(viewModel: PlaylistViewModel) {
    Row(modifier = Modifier.background(Color(55, 0, 179))) {
        viewModel.currentMusic.value?.let {
            Surface(modifier = Modifier.fillMaxHeight()) {
                Column {
                    // DisplayImage with aspect ratio 1:1
                    DisplayImage(
                        albumId = it.cover, modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .aspectRatio(1f)
                    )
                }
            }

            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(.5f)
                .padding(8.dp)) {


                Column {

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = it.artist,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )



                    
                    Row {

                        Text(
                            text = formatLongToMinutesSeconds(viewModel.currentPosition.value),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = formatLongToMinutesSeconds(viewModel.duration.value - viewModel.currentPosition.value),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Slider(
                        value = viewModel.progress.value,
                        onValueChange = {
                            viewModel.goTo(it)
                        },
                        valueRange = 0f..100f,
                        steps = 100,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                        IconButton(
                            onClick = { viewModel.previous() },
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                Icons.Default.SkipPrevious,
                                contentDescription = "Previous",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }

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
                                tint = Color.White,
                                modifier = Modifier.size(72.dp)
                            )
                        }
                        IconButton(
                            onClick = { viewModel.next() },
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                Icons.Default.SkipNext,
                                contentDescription = "Next",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))

                }


            }














        }
    }
}