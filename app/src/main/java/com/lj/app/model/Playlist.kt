package com.lj.app.model

import com.lj.app.R

class Playlist(var name: String = "Final Fantasy") {

    /***
     * Return a play list of type Music data class
     */
    fun getPlayList(): List<Music> {
        return listOf(
            Music(
                name = "Hollow",
                artist = "Final Fantasy 7 Remake",
                cover = R.drawable.album1,
                music = R.raw.hollow
            ),
            Music(
                name = "Suteki Da Ne",
                artist = "Final Fantasy X",
                cover = R.drawable.album2,
                music = R.raw.sutekidane
            ),
        )
    }
}
