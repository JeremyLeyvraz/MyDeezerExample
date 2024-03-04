package com.lj.app.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents a piece of music with its associated information.
 * @property name The name of the music.
 * @property artist The artist who created the music.
 * @property music The identifier for the music file.
 * @property cover The identifier for the cover image.
 * @property guid A unique identifier for the music.
 */
class Music(
    val name: String? = "",
    val artist: String? = "",
    val music: Int = 0,
    val cover: Int = 0,
    val guid: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(artist)
        parcel.writeInt(music)
        parcel.writeInt(cover)
        parcel.writeString(guid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Music> {
        override fun createFromParcel(parcel: Parcel): Music {
            return Music(parcel)
        }

        override fun newArray(size: Int): Array<Music?> {
            return arrayOfNulls(size)
        }
    }
}