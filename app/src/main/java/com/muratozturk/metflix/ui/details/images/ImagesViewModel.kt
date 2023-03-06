package com.muratozturk.metflix.ui.details.images

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Constants
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.domain.model.ImageUI
import com.muratozturk.metflix.domain.use_case.details.movie.images.GetMovieImagesUseCase
import com.muratozturk.metflix.domain.use_case.details.serie.images.GetSerieImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getSerieImagesUseCase: GetSerieImagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _images = MutableSharedFlow<Resource<List<ImageUI>>>()
    val images
        get() = _images

    init {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            savedStateHandle.get<MediaTypeEnum>(Constants.Arguments.MEDIA_TYPE)?.let { mediaType ->
                when (mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        getMovieImages(id)
                    }
                    MediaTypeEnum.SERIE -> {
                        getSerieImages(id)
                    }
                }
            }
        }
    }

    private fun getMovieImages(movieId: Int) = viewModelScope.launch {
        getMovieImagesUseCase(movieId).collectLatest {
            _images.emit(it)
        }
    }

    private fun getSerieImages(serieId: Int) = viewModelScope.launch {
        getSerieImagesUseCase(serieId).collectLatest {
            _images.emit(it)
        }
    }
}