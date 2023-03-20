package com.muratozturk.mova.data.model.remote.details.images


import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logos")
    val logos: List<Logo>,
    @SerializedName("posters")
    val posters: List<Poster>
)