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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lj.app.composable.image.DisplayImage
import com.lj.app.composable.music.MusicItemComposable
import com.lj.app.composable.player.RowCurrentMusicPlayerComposable
import com.lj.app.viewmodel.PlaylistViewModel

/**
 * A composable function responsible for displaying the playlist in a medium layout.
 * @param navController The navigation controller.
 * @param viewModel The view model containing data for the playlist.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistMediumComposable(navController: NavController, viewModel: PlaylistViewModel) {

    // Colors for UI elements
    val backgroundColor = Color.Black
    val textColor = Color.White

    Column(modifier = Modifier.background(backgroundColor)) {

        Row {

            LazyColumn(modifier = Modifier.padding(8.dp)) {

                item {

                    // Display playlist image
                    Surface(modifier = Modifier.fillMaxWidth()) {
                        DisplayImage(resourceId = viewModel.image,
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f))
                    }
                    // Playlist name
                    Text(
                        text = viewModel.name,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 40.sp,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                itemsIndexed(viewModel.getMusics()) { index, item ->

                    // Display music item
                    Surface(
                        modifier = Modifier.background(backgroundColor),
                        onClick = { viewModel.play(item.name) }) {
                        MusicItemComposable(item, viewModel.currentMusicName.value == item.name)
                    }

                    // Divider between music items
                    if (index < viewModel.getMusics().size - 1) {
                        Divider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
                    }
                }
                if(viewModel.isPlaying.value) {
                    item {
                        // Empty space to display the mini player without hiding the last item in the list
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(backgroundColor)
                        )
                    }
                }
            }

        }
    }
    if(viewModel.isPlaying.value) {
        // Display current music player if music is playing
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))
//            RowCurrentMusicPlayerComposable(navController = navController, viewModel = viewModel, modifier = Modifier
//                .fillMaxWidth())
        }
    }
}