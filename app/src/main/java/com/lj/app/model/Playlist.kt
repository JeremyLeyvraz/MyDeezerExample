package com.lj.app.model

import com.lj.app.R
import java.util.UUID

class Playlist(var name: String = "Final Fantasy") {

    val guid = UUID.randomUUID().toString()
    val image = R.drawable.finalfantasy

    /***
     * Return a play list of type Music data class
     */
    fun getPlayList(): List<Music> {
        return listOf(
            Music(
                name = "Hollow",
                artist = "Final Fantasy VII Remake",
                cover = R.drawable.finalfantasy7remake,
                music = R.raw.hollow,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Suteki Da Ne",
                artist = "Final Fantasy X",
                cover = R.drawable.finalfantasy10,
                music = R.raw.sutekidane,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Melodies Of Life",
                artist = "Final Fantasy IX",
                cover = R.drawable.finalfantasy9,
                music = R.raw.melodiesoflife,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Prelude",
                artist = "Final Fantasy VII Remake",
                cover = R.drawable.finalfantasy7remake,
                music = R.raw.prelude7,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "My Hands",
                artist = "Leona Lewis",
                cover = R.drawable.finalfantasy13,
                music = R.raw.myhands,
                guid = UUID.randomUUID().toString()
            ),
        )
    }
}
