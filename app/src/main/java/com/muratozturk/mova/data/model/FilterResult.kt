package com.muratozturk.mova.data.model

import android.os.Parcelable
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.remote.genres.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterResult(
    var type: MediaTypeEnum = MediaTypeEnum.MOVIE,
    var selectedGenreList: MutableList<Genre> = mutableListOf(),
    var sortBy: String = Constants.SortBy.POPULARITY,
    var includeAdult: Boolean = false,
    var genreList: List<Genre> = emptyList()
) : Parcelable
