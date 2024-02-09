package com.lj.app.composable.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lj.app.composable.image.DisplayImage
import com.lj.app.viewmodel.PlaylistViewModel

@Composable
fun PlaylistComposable(windowSizeClass: WindowSizeClass, navController: NavController, viewModel: PlaylistViewModel) {

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            PlaylistCompactComposable(navController, viewModel)
        }
        WindowWidthSizeClass.Medium -> {
            PlaylistMediumComposable(navController, viewModel)
        }
        WindowWidthSizeClass.Expanded -> {
            Text(text = "Expanded")
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
