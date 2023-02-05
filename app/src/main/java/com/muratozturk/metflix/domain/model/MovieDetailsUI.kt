package com.muratozturk.metflix.domain.model

import com.muratozturk.metflix.data.model.remote.details.movie.Genre

data class MovieDetailsUI(
    val backdropPath: String?,
    val genres: List<Genre>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)