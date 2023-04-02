package com.mobile.musicwiki.track.model

import com.google.gson.annotations.SerializedName
import com.mobile.musicwiki.album.model.Image
import com.mobile.musicwiki.artist.model.Artist

data class TrackResponse(
    @SerializedName("tracks", alternate = ["toptracks"]) var tracks: Tracks? = Tracks()
)

data class Track(
    @SerializedName("name") var name: String? = null,
    @SerializedName("artist") var artist: Artist? = Artist(),
    @SerializedName("image") var image: ArrayList<Image> = arrayListOf(),
)

data class Tracks(
    @SerializedName("track") var track: ArrayList<Track> = arrayListOf(),
)