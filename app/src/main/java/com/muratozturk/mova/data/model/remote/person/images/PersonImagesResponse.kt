package com.muratozturk.mova.data.model.remote.person.images


import com.google.gson.annotations.SerializedName

data class PersonImagesResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profiles")
    val profiles: List<Profile>
)