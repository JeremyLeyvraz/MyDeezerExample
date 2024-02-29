package com.lj.app.repository

import com.lj.app.model.Music
import com.lj.app.model.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioRepository {
    private val contentResolver: Playlist = Playlist()

    suspend fun getAudioData(): List<Music> = withContext(Dispatchers.IO) {
        contentResolver.getPlayList()
    }
}