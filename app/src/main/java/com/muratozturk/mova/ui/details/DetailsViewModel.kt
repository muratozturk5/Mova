package com.muratozturk.mova.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.domain.model.CastUI
import com.muratozturk.mova.domain.model.MovieDetailsUI
import com.muratozturk.mova.domain.model.SerieDetailsUI
import com.muratozturk.mova.domain.use_case.bookmark.AddBookmarkUseCase
import com.muratozturk.mova.domain.use_case.bookmark.IsBookmarkedUseCase
import com.muratozturk.mova.domain.use_case.bookmark.RemoveBookmarkUseCase
import com.muratozturk.mova.domain.use_case.details.movie.GetMovieCreditsUseCase
import com.muratozturk.mova.domain.use_case.details.movie.GetMovieDetailsUseCase
import com.muratozturk.mova.domain.use_case.details.serie.GetSerieCreditsUseCase
import com.muratozturk.mova.domain.use_case.details.serie.GetSerieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getSerieDetailsUseCase: GetSerieDetailsUseCase,
    private val getSerieCreditsUseCase: GetSerieCreditsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val isBookmarkedUseCase: IsBookmarkedUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _movieDetails = MutableSharedFlow<Resource<MovieDetailsUI>>()
    val movieDetails
        get() = _movieDetails.asSharedFlow()

    private var _movieCredits = MutableSharedFlow<Resource<List<CastUI>>>()
    val movieCredits
        get() = _movieCredits.asSharedFlow()

    private var _serieDetail = MutableSharedFlow<Resource<SerieDetailsUI>>()
    val serieDetail
        get() = _serieDetail.asSharedFlow()

    private var _serieCredits = MutableSharedFlow<Resource<List<CastUI>>>()
    val serieCredits
        get() = _serieCredits.asSharedFlow()

    private var _isBookmarked = MutableSharedFlow<Resource<Boolean>>()
    val isBookmarked
        get() = _isBookmarked.asSharedFlow()

    // Init is not used because when we navigate to back stack we need to get data again
    fun getDetails() {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            savedStateHandle.get<MediaTypeEnum>(Constants.Arguments.MEDIA_TYPE)?.let { mediaType ->
                when (mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        getMovieDetails(id)
                        getMovieCredits(id)
                    }
                    MediaTypeEnum.SERIE -> {
                        getSerieDetails(id)
                        getSerieCredits(id)
                    }
                }

                isBookmarked(id)

            }
        } ?: throw IllegalArgumentException("Missing id or media type")
    }

    private fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCase(movieId).collectLatest {
            _movieDetails.emit(it)
        }
    }

    private fun getMovieCredits(movieId: Int) = viewModelScope.launch {
        getMovieCreditsUseCase(movieId).collectLatest {
            _movieCredits.emit(it)
        }
    }

    private fun getSerieDetails(serieId: Int) = viewModelScope.launch {
        getSerieDetailsUseCase(serieId).collectLatest {
            _serieDetail.emit(it)
        }
    }

    private fun getSerieCredits(serieId: Int) = viewModelScope.launch {
        getSerieCreditsUseCase(serieId).collectLatest {
            _serieCredits.emit(it)
        }
    }

    fun addBookmark(bookmark: Bookmark) = viewModelScope.launch {
        addBookmarkUseCase(bookmark)
    }

    fun removeBookmark(id: Int) = viewModelScope.launch {
        removeBookmarkUseCase(id)
    }

    fun isBookmarked(id: Int) = viewModelScope.launch {
        isBookmarkedUseCase(id).collectLatest {
            _isBookmarked.emit(it)
        }
    }
}