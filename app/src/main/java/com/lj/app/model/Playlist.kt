package com.lj.app.model

import com.lj.app.R
import java.util.UUID

class Playlist(var name: String = "Final Fantasy") {

    val guid = UUID.randomUUID().toString()

    /***
     * Return a play list of type Music data class
     */
    fun getPlayList(): List<Music> {
        return listOf(
            Music(
                name = "Hollow",
                artist = "Final Fantasy 7 Remake",
                cover = R.drawable.album1,
                music = R.raw.hollow,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Suteki Da Ne",
                artist = "Final Fantasy X",
                cover = R.drawable.album2,
                music = R.raw.sutekidane,
                guid = UUID.randomUUID().toString()
            ),
        )
    }
}
