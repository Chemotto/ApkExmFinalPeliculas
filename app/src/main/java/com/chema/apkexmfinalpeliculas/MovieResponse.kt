package com.chema.apkexmfinalpeliculas

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val movies: List<Movie>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Error") val error: String?
)

data class Movie(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("imdbID") val imdbId: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("Poster") val poster: String?
)
