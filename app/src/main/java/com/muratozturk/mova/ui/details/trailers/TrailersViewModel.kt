package com.muratozturk.mova.ui.details.trailers

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.domain.model.VideoUI
import com.muratozturk.mova.domain.use_case.details.movie.trailers.GetMovieTrailersUseCase
import com.muratozturk.mova.domain.use_case.details.serie.trailers.GetSerieTrailersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailersViewModel @Inject constructor(
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
    private val getSerieTrailersUseCase: GetSerieTrailersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _trailers = MutableStateFlow<Resource<List<VideoUI>>>(Resource.Loading)
    val trailers
        get() = _trailers.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            savedStateHandle.get<MediaTypeEnum>(Constants.Arguments.MEDIA_TYPE)?.let { mediaType ->
                when (mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        getMovieTrailers(id)
                    }
                    MediaTypeEnum.SERIE -> {
                        getSerieTrailers(id)
                    }
                }
            }
        }
    }

    private fun getMovieTrailers(movieId: Int) = viewModelScope.launch {
        getMovieTrailersUseCase(movieId).collectLatest {
            _trailers.emit(it)
        }
    }

    private fun getSerieTrailers(serieId: Int) = viewModelScope.launch {
        getSerieTrailersUseCase(serieId).collectLatest {
            _trailers.emit(it)
        }
    }
}