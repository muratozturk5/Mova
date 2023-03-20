package com.muratozturk.mova.ui.details.images

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.domain.model.ImageUI
import com.muratozturk.mova.domain.use_case.details.movie.images.GetMovieImagesUseCase
import com.muratozturk.mova.domain.use_case.details.serie.images.GetSerieImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getSerieImagesUseCase: GetSerieImagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _images = MutableStateFlow<Resource<List<ImageUI>>>(Resource.Loading)
    val images
        get() = _images.asStateFlow()

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