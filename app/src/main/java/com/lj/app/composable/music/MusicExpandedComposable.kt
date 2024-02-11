package com.lj.app.composable.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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

@Composable
fun MusicExpandedComposable(viewModel: PlaylistViewModel) {

    val backgroundColor = LocalContext.current.resources.getColor(R.color.purple_700, null)
    val textColor = Color.White

    Row(modifier = Modifier.background(Color(backgroundColor))) {
        viewModel.currentMusic.value?.let {

            Surface(modifier = Modifier.fillMaxHeight().padding(8.dp)) {
                Column {
                    // DisplayImage with aspect ratio 1:1
                    DisplayImage(
                        albumId = it.cover, modifier = Modifier
                            .fillMaxHeight()
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
                            color = textColor,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = it.artist,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = textColor,
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

                    Slider(
                        value = viewModel.progress.value,
                        onValueChange = {
                            viewModel.goTo(it)
                        },
                        valueRange = 0f..100f,
                        steps = 100,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Command previous play/pause next
                    RowMainCommandComposable(viewModel, Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.weight(1f))

                }

            }

        }
    }
}