package com.muratozturk.mova.domain.model

data class PersonDetailsUI(
    val alsoKnownAs: List<String>,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    val imdbİd: String?,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double,
    val profilePath: String?
)