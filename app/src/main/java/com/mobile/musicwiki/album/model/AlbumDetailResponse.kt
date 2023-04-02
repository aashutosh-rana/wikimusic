package com.mobile.musicwiki.album.model

import com.google.gson.annotations.SerializedName
import com.mobile.musicwiki.genre.model.Description
import com.mobile.musicwiki.genre.model.Toptags

data class AlbumDetailResponse(
    @SerializedName("album") var album: AlbumDetail? = AlbumDetail()
)

data class AlbumDetail(
    @SerializedName("artist") var artist: String? = null,
    @SerializedName("tags") var tags: Toptags? = Toptags(),
    @SerializedName("image") var image: ArrayList<Image> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("wiki") var wiki: Description? = Description()
)