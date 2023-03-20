package com.muratozturk.mova.data.model.remote.person.movies


import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("credit_id")
    val creditİd: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("genre_ids")
    val genreİds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("job")
    val job: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)