package com.lj.app.model

class Playlist(var name: String = "Playlist") {


    var songs : List<Song> = mutableListOf()

    fun add(song: Song) {
        songs = songs.plus(song)
    }

    fun getSong(albumId: Int): Song = songs.first { it.albumId == albumId }
}
