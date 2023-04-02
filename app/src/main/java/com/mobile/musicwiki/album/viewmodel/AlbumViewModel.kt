package com.mobile.musicwiki.album.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.album.model.AlbumDetailResponse
import com.mobile.musicwiki.album.model.AlbumResponse
import com.mobile.musicwiki.network.NetworkService
import com.mobile.musicwiki.network.Response
import com.mobile.musicwiki.network.ResponseUtil
import com.mobile.musicwiki.network.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val networkService: NetworkService) : ViewModel() {

    private val albumResponse: MutableLiveData<ResponseWrapper<AlbumResponse>> by lazy {
        MutableLiveData<ResponseWrapper<AlbumResponse>>()
    }

    fun getAlbumResponse(): LiveData<ResponseWrapper<AlbumResponse>> = albumResponse

    fun fetchAlbums(
        method: String = "tag.gettopalbums",
        genre: String? = null,
        artist: String? = null
    ) {
        albumResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getTopAlbums(
                    method = method,
                    artist = artist,
                    genre = genre, api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<AlbumResponse> = when (response) {
                is Response.Success -> ResponseWrapper.Success(response.data)
                is Response.Error -> ResponseWrapper.Error(cause = response.cause)
            }
            albumResponse.postValue(responseWrapper)
        }
    }

    private val albumDetailResponse: MutableLiveData<ResponseWrapper<AlbumDetailResponse>> by lazy {
        MutableLiveData<ResponseWrapper<AlbumDetailResponse>>()
    }

    fun getAlbumDetailResponse(): LiveData<ResponseWrapper<AlbumDetailResponse>> =
        albumDetailResponse

    fun fetchAlbumDetail(album: String, artist: String) {
        albumDetailResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getAlbumDetail(
                    album = album, artist = artist, api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<AlbumDetailResponse> = when (response) {
                is Response.Success -> ResponseWrapper.Success(response.data)
                is Response.Error -> ResponseWrapper.Error(cause = response.cause)
            }
            albumDetailResponse.postValue(responseWrapper)
        }
    }

}