package com.mobile.musicwiki.album.model

import com.google.gson.annotations.SerializedName
import com.mobile.musicwiki.artist.model.Artist

data class AlbumResponse(
    @SerializedName("albums", alternate = ["topalbums"]) var albums: Albums? = Albums(),
)

data class Image(
    @SerializedName("#text") var text: String? = null,
    @SerializedName("size") var size: String? = null
)

data class Album(
    @SerializedName("name") var name: String,
    @SerializedName("artist") var artist: Artist = Artist(),
    @SerializedName("image") var image: List<Image> = listOf(),
)

data class Albums(
    @SerializedName("album") var album: List<Album> = listOf(),
)