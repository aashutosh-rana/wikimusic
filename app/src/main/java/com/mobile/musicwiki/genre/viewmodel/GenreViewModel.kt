package com.mobile.musicwiki.genre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.genre.model.GenreDetailResponse
import com.mobile.musicwiki.genre.model.GenreResponse
import com.mobile.musicwiki.network.NetworkService
import com.mobile.musicwiki.network.Response
import com.mobile.musicwiki.network.ResponseUtil
import com.mobile.musicwiki.network.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val networkService: NetworkService) : ViewModel() {

    private val allGenreResponse: MutableLiveData<ResponseWrapper<GenreResponse>> by lazy {
        MutableLiveData<ResponseWrapper<GenreResponse>>()
    }

    fun getAllGenreResponse(): LiveData<ResponseWrapper<GenreResponse>> =
        allGenreResponse

    fun fetchAllGenres() {
        allGenreResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getGenreList(
                    api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<GenreResponse> =
                when (response) {
                    is Response.Success -> ResponseWrapper.Success(response.data)
                    is Response.Error -> ResponseWrapper.Error(cause = response.cause)
                }
            allGenreResponse.postValue(responseWrapper)
        }
    }

    private val genreDetailResponse: MutableLiveData<ResponseWrapper<GenreDetailResponse>> by lazy {
        MutableLiveData<ResponseWrapper<GenreDetailResponse>>()
    }

    fun getGenreDetailResponse(): LiveData<ResponseWrapper<GenreDetailResponse>> =
        genreDetailResponse

    fun fetchGenreDetail(genre: String) {
        genreDetailResponse.value = ResponseWrapper.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ResponseUtil.getResponseSafely {
                networkService.getGenreDetails(
                    genre = genre,
                    api_key = BuildConfig.apiKey
                )
            }
            val responseWrapper: ResponseWrapper<GenreDetailResponse> =
                when (response) {
                    is Response.Success -> ResponseWrapper.Success(response.data)
                    is Response.Error -> ResponseWrapper.Error(cause = response.cause)
                }
            genreDetailResponse.postValue(responseWrapper)
        }
    }

}