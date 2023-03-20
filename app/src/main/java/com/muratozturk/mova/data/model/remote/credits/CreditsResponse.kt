package com.muratozturk.mova.data.model.remote.credits


import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)