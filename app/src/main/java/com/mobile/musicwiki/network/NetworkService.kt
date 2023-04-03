package com.mobile.musicwiki.network

import com.mobile.musicwiki.album.model.AlbumDetailResponse
import com.mobile.musicwiki.album.model.AlbumResponse
import com.mobile.musicwiki.artist.model.ArtistDetailResponse
import com.mobile.musicwiki.artist.model.ArtistResponse
import com.mobile.musicwiki.genre.model.GenreDetailResponse
import com.mobile.musicwiki.genre.model.GenreResponse
import com.mobile.musicwiki.track.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("2.0/")
    suspend fun getGenreList(
        @Query("method") method: String = "tag.getTopTags",
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): GenreResponse

    @GET("2.0/")
    suspend fun getGenreDetails(
        @Query("method") method: String = "tag.getinfo",
        @Query("tag") genre: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): GenreDetailResponse

    @GET("2.0/")
    suspend fun getTopAlbums(
        @Query("method") method: String,
        @Query("tag") genre: String? = null,
        @Query("artist") artist: String? = null,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): AlbumResponse

    @GET("2.0/")
    suspend fun getAlbumDetail(
        @Query("method") method: String = "album.getinfo",
        @Query("artist") artist: String,
        @Query("album") album: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): AlbumDetailResponse

    @GET("2.0/")
    suspend fun getTopArtist(
        @Query("method") method: String = "tag.gettopartists",
        @Query("tag") genre: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): ArtistResponse

    @GET("2.0/")
    suspend fun getArtistDetail(
        @Query("method") method: String = "artist.getinfo",
        @Query("artist") artist: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): ArtistDetailResponse

    @GET("2.0/")
    suspend fun getTopTrack(
        @Query("method") method: String,
        @Query("tag") genre: String? = null,
        @Query("artist") artist: String? = null,
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json"
    ): TrackResponse
}