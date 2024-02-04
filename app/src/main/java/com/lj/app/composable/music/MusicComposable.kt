package com.lj.app.composable.music

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.viewmodel.MusicViewModel

@Composable
fun MusicComposable(windowSizeClass: WindowSizeClass, viewModel: MusicViewModel) {



    Text(text = "Titre music")

    /**
     * TODO:
     * A partir de l'ID envoyé par le clic depuis la vue Playlist,
     * récupérer toutes les infos et les afficher
     *
     */
}