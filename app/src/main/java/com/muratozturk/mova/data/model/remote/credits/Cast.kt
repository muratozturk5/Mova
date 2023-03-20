package com.muratozturk.mova.data.model.remote.credits


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("cast_id")
    val castİd: Int,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val creditİd: String,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
)