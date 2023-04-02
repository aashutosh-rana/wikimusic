package com.mobile.musicwiki.album.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@kotlinx.parcelize.Parcelize
data class AlbumQuery(@SerializedName("") val albumName: String?, val artistName: String?) :
    Parcelable
