package com.muratozturk.mova.data.model.remote.configurations


import com.google.gson.annotations.SerializedName

data class LanguageItem(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)