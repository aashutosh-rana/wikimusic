package com.mobile.musicwiki.network

sealed class Response<T>(success: Boolean) {
    class Success<T>(val data: T?) : Response<T>(true)
    class Error<T>(val cause: Throwable? = null) : Response<T>(true)
}

sealed class ResponseWrapper<T>(val data: T? = null) {
    class Success<T>(data: T?) : ResponseWrapper<T>(data)
    class Loading<T>(data: T? = null) : ResponseWrapper<T>(data)
    class Error<T>(val cause: Throwable? = null, data: T? = null) : ResponseWrapper<T>(data)
}
