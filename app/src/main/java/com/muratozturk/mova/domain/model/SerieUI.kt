package com.muratozturk.mova.domain.model

data class SerieUI(
    val backdropPath: String?,
    val id: Int,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val voteAverage: Double,
    val firstAirDate: String
)
