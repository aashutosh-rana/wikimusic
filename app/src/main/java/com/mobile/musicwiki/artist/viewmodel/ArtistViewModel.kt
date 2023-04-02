package com.mobile.musicwiki.artist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.artist.model.ArtistDetailResponse
import com.mobile.musicwiki.artist.model.ArtistResponse
import com.mobile.musicwiki.network.NetworkService
import com.mobile.musicwiki.network.Response
import com.mobile.musicwiki.network.ResponseUtil
import com.mobile.musicwiki.network.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {

    private val artistResponse: MutableLiveData<ResponseWrapper<ArtistResponse>> by lazy {
        MutableLiveData<ResponseWrapper<ArtistResponse>>()
    }

    fun getArtistResponse(): LiveData<ResponseWrapper<ArtistResponse>> = artistResponse

    fun fetchArtist(genre: String) {
        artistResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getTopArtist(
                    genre = genre, api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<ArtistResponse> = when (response) {
                is Response.Success -> ResponseWrapper.Success(response.data)
                is Response.Error -> ResponseWrapper.Error(cause = response.cause)
            }
            artistResponse.postValue(responseWrapper)
        }
    }

    private val artistDetailResponse: MutableLiveData<ResponseWrapper<ArtistDetailResponse>> by lazy {
        MutableLiveData<ResponseWrapper<ArtistDetailResponse>>()
    }

    fun getArtistDetailResponse(): LiveData<ResponseWrapper<ArtistDetailResponse>> =
        artistDetailResponse

    fun fetchArtistDetail(artist: String) {
        artistDetailResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getArtistDetail(
                    artist = artist, api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<ArtistDetailResponse> = when (response) {
                is Response.Success -> ResponseWrapper.Success(response.data)
                is Response.Error -> ResponseWrapper.Error(cause = response.cause)
            }
            artistDetailResponse.postValue(responseWrapper)
        }
    }
}