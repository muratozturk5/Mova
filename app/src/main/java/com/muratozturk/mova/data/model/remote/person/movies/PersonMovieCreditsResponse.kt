package com.muratozturk.mova.data.model.remote.person.movies


import com.google.gson.annotations.SerializedName

data class PersonMovieCreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)