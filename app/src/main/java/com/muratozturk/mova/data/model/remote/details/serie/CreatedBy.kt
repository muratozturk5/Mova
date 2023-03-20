package com.muratozturk.mova.data.model.remote.details.serie


import com.google.gson.annotations.SerializedName

data class CreatedBy(
    @SerializedName("credit_id")
    val creditİd: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)