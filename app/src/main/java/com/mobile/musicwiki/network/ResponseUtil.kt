package com.mobile.musicwiki.network

object ResponseUtil {
    suspend fun <T> getResponseSafely(getResponse: suspend () -> T): Response<T> {
        return try {
            Response.Success(getResponse())
        } catch (e: java.lang.Exception) {
            Response.Error(e)
        }
    }
}