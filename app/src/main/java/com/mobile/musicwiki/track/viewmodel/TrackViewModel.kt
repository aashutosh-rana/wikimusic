package com.mobile.musicwiki.track.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.track.model.TrackResponse
import com.mobile.musicwiki.network.NetworkService
import com.mobile.musicwiki.network.Response
import com.mobile.musicwiki.network.ResponseUtil
import com.mobile.musicwiki.network.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val networkService: NetworkService) : ViewModel() {

    private val trackResponse: MutableLiveData<ResponseWrapper<TrackResponse>> by lazy {
        MutableLiveData<ResponseWrapper<TrackResponse>>()
    }

    fun getTrackResponse(): LiveData<ResponseWrapper<TrackResponse>> = trackResponse

    fun fetchTracks(
        method: String = "tag.gettoptracks",
        genre: String? = null,
        artist: String? = null
    ) {
        trackResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getTopTrack(
                    method = method,
                    artist = artist,
                    genre = genre, api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<TrackResponse> = when (response) {
                is Response.Success -> ResponseWrapper.Success(response.data)
                is Response.Error -> ResponseWrapper.Error(cause = response.cause)
            }
            trackResponse.postValue(responseWrapper)
        }
    }

}