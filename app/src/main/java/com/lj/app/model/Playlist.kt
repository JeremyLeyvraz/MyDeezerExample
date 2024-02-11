package com.lj.app.model

import com.lj.app.R
import java.util.UUID

/**
 * Represents a playlist containing music tracks.
 * @property name The name of the playlist.
 * @property image The image associated with the playlist.
 */
class Playlist(var name: String = "Final Fantasy", var image : Int = R.drawable.finalfantasy) {

    /**
     * Retrieves the list of music tracks in the playlist.
     * @return A list of [Music] objects representing the tracks in the playlist.
     */
    fun getPlayList(): List<Music> {
        return listOf(
            Music(
                name = "Hollow",
                artist = "Final Fantasy VII Remake",
                cover = R.drawable.finalfantasy7,
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
                cover = R.drawable.finalfantasy7,
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
            Music(
                name = "Eternity",
                artist = "Final Fantasy X-2",
                cover = R.drawable.finafantasy102,
                music = R.raw.eternity,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Eyes on me",
                artist = "Final Fantasy VIII",
                cover = R.drawable.finalfantasy8,
                music = R.raw.eyesonme,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "Main theme",
                artist = "Final Fantasy XII",
                cover = R.drawable.finalfantasy12,
                music = R.raw.mainthemexii,
                guid = UUID.randomUUID().toString()
            ),
            Music(
                name = "To Zanarkand",
                artist = "Final Fantasy X",
                cover = R.drawable.finalfantasy10,
                music = R.raw.tozanarkand,
                guid = UUID.randomUUID().toString()
            ),
        )
    }
}
