package com.lj.app.composable.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lj.app.composable.image.DisplayImage
import com.lj.app.model.Music
import com.lj.app.viewmodel.PlaylistViewModel

@Composable
fun PlaylistComposable(windowSizeClass: WindowSizeClass, navController: NavController, viewModel: PlaylistViewModel) {

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Compact(navController, viewModel)
        }
        WindowWidthSizeClass.Medium -> {
            Medium(navController, viewModel)
        }
        WindowWidthSizeClass.Expanded -> {
            Expanded(navController, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Compact(navController: NavController, viewModel: PlaylistViewModel) {
    Column() {
//        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//            Surface(modifier = Modifier.fillMaxWidth()) {
//                DisplayImage(albumId = viewModel.image, Modifier.fillMaxWidth().aspectRatio(1f))
//            }
//        }
//        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//            Text(
//                text = viewModel.name,
//                style = MaterialTheme.typography.labelLarge.copy(
//                    fontSize = 40.sp, // Set your desired text size
//                    fontWeight = FontWeight.Bold // Make the text bold
//                )
//            )
//        }
//        Row {
//            Text(text = viewModel.currentMusicName.value)
//        }
        Row {
            LazyColumn {


                item {
                        Surface(modifier = Modifier.fillMaxWidth()) {
                            DisplayImage(albumId = viewModel.image, Modifier.fillMaxWidth().aspectRatio(1f))
                        }

                        Text(
                            text = viewModel.name,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 40.sp, // Set your desired text size
                                fontWeight = FontWeight.Bold // Make the text bold
                            )
                        )

                        Text(text = viewModel.currentMusicName.value)

                }

                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                }
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                }
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                }
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Medium(navController: NavController, viewModel: PlaylistViewModel) {
    Row {
        Column {
            Text(text = "Titre playlist")
            Text(text = viewModel.currentMusicName.value)
        }
        Column {
            LazyColumn {
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                }
            }
        }
    }
}

@Composable
fun MusicComposable(music: Music, isPlaying: Boolean) {
    Row {
        DisplayImage(albumId = music.cover, modifier = Modifier
            .width(80.dp)
            .height(80.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)) {
            Text(text = music.name)
            Text(text = music.artist)
        }
        if(isPlaying) {
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            ) {
                Text(text = "EN COURS")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Expanded(navController: NavController, viewModel: PlaylistViewModel) {
    Row {
        Column {
            Text(text = "Titre playlist")
            Text(text = viewModel.currentMusicName.value)
        }
        Column {
            LazyColumn {
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        //navController.navigate(MUSIC_DESTINATION+"/"+viewModel.guid+"/"+item.music)
                        viewModel.play(item.name)
                    }) {
                        Text(text = item.name)
                    }
                }
            }
        }
    }
}
