package com.muratozturk.mova.ui.person.images

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.domain.model.ImageUI
import com.muratozturk.mova.domain.use_case.person.images.GetPersonImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonImagesViewModel @Inject constructor(
    private val getPersonImagesUseCase: GetPersonImagesUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _images = MutableStateFlow<Resource<List<ImageUI>>>(Resource.Loading)
    val images
        get() = _images.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constants.Arguments.ID)?.let { id ->
            getPersonImages(id)
        }
    }

    private fun getPersonImages(id: Int) = viewModelScope.launch {
        getPersonImagesUseCase(id).collectLatest {
            _images.emit(it)
        }
    }
}