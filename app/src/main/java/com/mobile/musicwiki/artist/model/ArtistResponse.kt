package com.mobile.musicwiki.artist.model

import com.google.gson.annotations.SerializedName
import com.mobile.musicwiki.album.model.Image

data class ArtistResponse(
    @SerializedName("topartists") var topartists: Topartists? = Topartists()
)

data class Artist(
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: ArrayList<Image> = arrayListOf(),
)


data class Topartists(
    @SerializedName("artist") var artist: ArrayList<Artist> = arrayListOf(),
)