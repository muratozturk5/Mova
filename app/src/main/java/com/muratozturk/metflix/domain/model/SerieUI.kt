package com.muratozturk.metflix.domain.model

data class SerieUI(
    val backdropPath: String?,
    val id: Int,
    val name: String,
    val originalName: String,
    val posterPath: String,
    val voteAverage: Double
)
