package com.lj.app.composable.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lj.app.navigation.MUSIC_DESTINATION
import com.lj.app.navigation.MainNavigation
import com.lj.app.viewmodel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistComposable(windowSizeClass: WindowSizeClass, navController: NavController, viewModel: PlaylistViewModel) {

    Column {
        Row {
            Text(text = "Titre playlist")
        }
        Row {
            LazyColumn {
                items(viewModel.getMusics()) { item ->
                    Card(onClick = {
                        navController.navigate(MUSIC_DESTINATION+"/"+item.music)
                    }) {
                        Text(text = item.name)
                    }
                }
            }
        }
    }




}