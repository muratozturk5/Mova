package com.muratozturk.mova.ui.details.similar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.domain.model.MovieUI
import com.muratozturk.mova.domain.model.SerieUI
import com.muratozturk.mova.domain.use_case.details.movie.similar.GetSimilarMoviesUseCase
import com.muratozturk.mova.domain.use_case.details.serie.similar.GetSimilarSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _similarMovies: MutableStateFlow<PagingData<MovieUI>> =
        MutableStateFlow(PagingData.empty())
    val similarMovies
        get() = _similarMovies.asStateFlow()


    private val _similarSeries: MutableStateFlow<PagingData<SerieUI>> =
        MutableStateFlow(PagingData.empty())
    val similarSeries
        get() = _similarSeries.asStateFlow()


    init {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            savedStateHandle.get<MediaTypeEnum>(Constants.Arguments.MEDIA_TYPE)?.let { mediaType ->
                when (mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        getSimilarMovies(id)
                    }
                    MediaTypeEnum.SERIE -> {
                        getSimilarSeries(id)
                    }
                }
            }
        }
    }


    private fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        getSimilarMoviesUseCase(movieId).cachedIn(viewModelScope).collectLatest {
            _similarMovies.emit(it)
        }
    }

    private fun getSimilarSeries(serieId: Int) = viewModelScope.launch {
        getSimilarSeriesUseCase(serieId).cachedIn(viewModelScope).collectLatest {
            _similarSeries.emit(it)
        }
    }


}