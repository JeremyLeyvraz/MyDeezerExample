package com.lj.app.model

/**
 * Represents a piece of music with its associated information.
 * @property name The name of the music.
 * @property artist The artist who created the music.
 * @property music The identifier for the music file.
 * @property cover The identifier for the cover image.
 * @property guid A unique identifier for the music.
 */
data class Music(
    val name: String = "",
    val artist: String = "",
    val music: Int = 0,
    val cover: Int = 0,
    val guid: String = ""
)