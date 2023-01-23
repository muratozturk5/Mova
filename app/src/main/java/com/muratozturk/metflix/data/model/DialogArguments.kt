package com.muratozturk.metflix.data.model

import androidx.navigation.NavDirections

data class DialogArguments(
    val title: String,
    val message: String,
    val imageResource: Int,
    val navigationDestination: NavDirections
) : java.io.Serializable
