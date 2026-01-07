package com.chema.apkexmfinalpeliculas

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("type") type: String = "movie"
    ): Call<MovieResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Call<MovieDetail>
}