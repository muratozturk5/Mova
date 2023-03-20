package com.muratozturk.mova.data.model.remote.person.images


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,
    @SerializedName("file_path")
    val filePath: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("iso_639_1")
    val iso6391: Any?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("width")
    val width: Int
)