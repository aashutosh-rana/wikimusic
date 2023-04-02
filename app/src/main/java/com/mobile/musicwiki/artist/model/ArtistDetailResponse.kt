package com.mobile.musicwiki.artist.model

import com.google.gson.annotations.SerializedName
import com.mobile.musicwiki.album.model.Albums
import com.mobile.musicwiki.album.model.Image
import com.mobile.musicwiki.genre.model.Toptags

data class ArtistDetailResponse(
    @SerializedName("artist") var artist: ArtistDetails? = ArtistDetails()
)

data class Stats(
    @SerializedName("listeners") var listeners: String? = null,
    @SerializedName("playcount") var playcount: String? = null
)

data class Bio(
    @SerializedName("published") var published: String? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("content") var content: String? = null
)

data class ArtistDetails(
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: ArrayList<Image> = arrayListOf(),
    @SerializedName("stats") var stats: Stats? = Stats(),
    @SerializedName("tags") var tags: Toptags? = Toptags(),
    @SerializedName("bio") var bio: Bio? = Bio()
)

data class ArtistTopAlbumsResponse(
    @SerializedName("topalbums") var topalbums: Albums? = Albums()
)