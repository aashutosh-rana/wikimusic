package com.mobile.musicwiki.genre.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("toptags") var toptags: Toptags = Toptags()
)

data class Tag(
    @SerializedName("name") var name: String,
)

data class Toptags(
    @SerializedName("tag") var tag: List<Tag> = listOf()
)