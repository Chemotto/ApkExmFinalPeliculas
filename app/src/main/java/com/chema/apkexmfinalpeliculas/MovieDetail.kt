package com.chema.apkexmfinalpeliculas

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Runtime") val runtime: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("imdbID") val imdbId: String?
)
