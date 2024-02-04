package com.lj.app.viewmodel

import androidx.lifecycle.ViewModel
import com.lj.app.model.Music
import com.lj.app.model.Playlist
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(): ViewModel() {

    val playlist = Playlist()

    val guid = playlist.guid

    fun getMusics(): List<Music> {
        return playlist.getPlayList()
    }

}