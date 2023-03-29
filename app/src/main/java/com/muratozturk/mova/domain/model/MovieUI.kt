package com.muratozturk.mova.domain.model

data class MovieUI(
    val adult: Boolean,
    val id: Int,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val title: String,
    val voteAverage: Double,
    var isBookmarked: Boolean = false,
    val releaseDate: String?
)
