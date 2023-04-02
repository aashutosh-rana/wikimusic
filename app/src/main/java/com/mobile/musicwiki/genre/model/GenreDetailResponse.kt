package com.mobile.musicwiki.genre.model

import com.google.gson.annotations.SerializedName

data class GenreDetailResponse(
    @SerializedName("tag") var tag: Genre? = Genre()
)

data class Description(
    @SerializedName("summary") var summary: String? = null
)

data class Genre(
    @SerializedName("name") var name: String? = null,
    @SerializedName("wiki") var wiki: Description? = Description()
)