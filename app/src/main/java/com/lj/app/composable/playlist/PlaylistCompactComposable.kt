package com.lj.app.composable.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lj.app.R
import com.lj.app.composable.image.CustomIcon
import com.lj.app.composable.image.DisplayImage
import com.lj.app.composable.music.MusicItemComposable
import com.lj.app.navigation.MUSIC_DESTINATION
import com.lj.app.viewmodel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistCompactComposable(navController: NavController, viewModel: PlaylistViewModel) {
    Column(modifier = Modifier.background(Color.Black)) {
        Row {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item {
                    Surface(modifier = Modifier.fillMaxWidth()) {
                        DisplayImage(albumId = viewModel.image,
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f))
                    }
                    Text(
                        text = viewModel.name,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 40.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                itemsIndexed(viewModel.getMusics()) { index, item ->
                    Surface(
                        modifier = Modifier.background(Color.Black),
                        onClick = { viewModel.play(item.name) }) {
                        MusicItemComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                    // Add a separator if the item is not the last one
                    if (index < viewModel.getMusics().size - 1) {
                        Divider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
                    }
                }
                if(viewModel.isPlaying.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }

        }
    }
    if(viewModel.isPlaying.value) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Card(modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
                onClick = {
                    navController.navigate(MUSIC_DESTINATION)
                }) {

                Row(modifier = Modifier.background(Color(55,0,179))) {
                    // Icône en bas, superposée à la colonne
                    IconButton(
                        onClick =
                        {
                            viewModel.pause()
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        if(viewModel.isPause.value) {
                            CustomIcon(icon = R.drawable.btn_jouer, size = 48)
                        } else {
                            CustomIcon(icon = R.drawable.btn_pause, size = 48)
                        }
                    }

                    viewModel.currentMusic.value?.let {
                        Box(modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)) {
                            Column {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                    )
                                )
                                Text(
                                    text = it.artist,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                    )
                                )
                            }
                        }
                    }
                    IconButton(
                        onClick = { viewModel.next() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        CustomIcon(icon = R.drawable.btn_suivant, size = 48)
                    }
                }
            }
        }
    }
}