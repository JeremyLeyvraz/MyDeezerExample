package com.lj.app.composable

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.lj.app.model.Album
import com.lj.app.model.Playlist
import com.lj.app.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model to get a text using [HelloUseCase].
 */
@HiltViewModel
class HelloViewModel @Inject constructor(): ViewModel() {
    fun getText() = "Jeremy"

    private var playlist = Playlist("Jeux vidéo")
    private var album1 = Album(1, "Final Fantasy 7 Remake", "album1")
    private var album2 = Album(2, "Final Fantasy 10", "album2")

    lateinit var currentSong: Song

    var exoPlayer: ExoPlayer? = null

    fun init(context: Context) {

        exoPlayer = SimpleExoPlayer.Builder(context).build()


        playlist.add(Song(album1.id, "hollow", "Hollow"))
        playlist.add(Song(album2.id, "sutekidane", "Suteki Da Ne"))

        currentSong = playlist.getSong(album1.id)
    }

    fun next() {
        if(currentSong.albumId == album1.id) {
            currentSong = playlist.getSong(album2.id)
        } else {
            currentSong = playlist.getSong(album1.id)
        }
    }
}
