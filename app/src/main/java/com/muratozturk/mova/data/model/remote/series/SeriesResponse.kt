package com.muratozturk.mova.data.model.remote.series


import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Serie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)