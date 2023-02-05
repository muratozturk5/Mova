package com.muratozturk.metflix.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Constants
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.domain.model.CastUI
import com.muratozturk.metflix.domain.model.MovieDetailsUI
import com.muratozturk.metflix.domain.use_case.details.movie.GetMovieCreditsUseCase
import com.muratozturk.metflix.domain.use_case.details.movie.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private var _movieDetails = MutableSharedFlow<Resource<MovieDetailsUI>>()
    val movieDetails
        get() = _movieDetails.asSharedFlow()

    private var _movieCredits = MutableSharedFlow<Resource<List<CastUI>>>()
    val movieCredits
        get() = _movieCredits.asSharedFlow()

    private var _serieDetail = MutableSharedFlow<Resource<MovieDetailsUI>>()
    val serieDetail
        get() = _serieDetail.asSharedFlow()

    init {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            savedStateHandle.get<MediaTypeEnum>(Constants.Arguments.MEDIA_TYPE)?.let { mediaType ->
                Timber.d("movie details: $id")

                when (mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        getMovieDetails(id)
                        getMovieCredits(id)
                    }
                    MediaTypeEnum.SERIE -> {

                    }
                }


            }
        }
    }

    private fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCase(movieId).collectLatest {
            _movieDetails.emit(it)
            Timber.d("movie details: $it")
        }
    }

    private fun getMovieCredits(movieId: Int) = viewModelScope.launch {
        getMovieCreditsUseCase(movieId).collectLatest {
            _movieCredits.emit(it)
            Timber.d("movie credits: $it")
        }
    }
}