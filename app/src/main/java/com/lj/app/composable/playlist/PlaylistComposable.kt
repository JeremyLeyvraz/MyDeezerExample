package com.lj.app.composable.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.lj.app.R
import com.lj.app.composable.image.CustomIcon
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
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
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
                .fillMaxWidth()) {

                Row(modifier = Modifier.background(Color(55,0,179))) {
                    // Icône en bas, superposée à la colonne
                    IconButton(
                        onClick =
                        {
                            if(viewModel.isPause.value) {
                                viewModel.pause()
                            } else {
                                viewModel.pause()
                            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Medium(navController: NavController, viewModel: PlaylistViewModel) {
    Row {
        Surface(modifier = Modifier.fillMaxWidth(0.3F)) {
            Column(modifier = Modifier.background(Color.Black)) {
                // DisplayImage with aspect ratio 1:1
                DisplayImage(albumId = viewModel.image, modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .weight(1f)
                    .aspectRatio(1f)
                )

                // Text component
                Text(
                    text = viewModel.name,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                        .weight(0.2f) // Adjust the weight as needed for proper distribution
                )
                Slider(
                    value = 0.5F, // Mettre à jour pour changer le slider
                    onValueChange = { newValue ->
                        //onSliderValueChange(newValue)
                    },
                    valueRange = 0F..1F,
                    steps = 100,
                    enabled = true,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Green,
                        activeTrackColor = Color.Green,
                        inactiveTrackColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            LazyColumn {
                itemsIndexed(viewModel.getMusics()) { index, item ->

                    Surface(
                        modifier = Modifier.background(Color.Black),
                        onClick = { viewModel.play(item.name) }) {
                        MusicComposable(item, viewModel.currentMusicName.value == item.name)
                    }
                    // Add a separator if the item is not the last one
                    if (index < viewModel.getMusics().size - 1) {
                        Divider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Expanded(navController: NavController, viewModel: PlaylistViewModel) {
    Row {
        Column {
            Surface(modifier = Modifier.fillMaxWidth()) {
                DisplayImage(albumId = viewModel.image,
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f))
            }
            Text(
                text = viewModel.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 40.sp, // Set your desired text size
                    fontWeight = FontWeight.Bold, // Make the text bold,
                    color = Color.White
                )
            )
            Text(text = viewModel.currentMusicName.value)
        }
        Column(modifier = Modifier.padding(8.dp)) {
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

@Composable
fun MusicComposable(music: Music, isPlaying: Boolean) {
    Row(modifier = Modifier.background(Color.Black)) {
        DisplayImage(albumId = music.cover, modifier = Modifier
            .width(80.dp)
            .height(80.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .align(Alignment.CenterVertically)
            .weight(1.0f)) {
            Text(
                text = music.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White,
                    fontSize = 20.sp,
                )
            )
            Text(
                text = music.artist,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White,
                    fontSize = 14.sp,
                )
            )
        }
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
