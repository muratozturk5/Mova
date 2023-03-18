package com.muratozturk.metflix.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.data.model.remote.genres.Genre
import com.muratozturk.metflix.domain.use_case.filter.GetMovieGenresUseCase
import com.muratozturk.metflix.domain.use_case.filter.GetSerieGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSerieGenresUseCase: GetSerieGenresUseCase
) : ViewModel() {

    private val _genres: MutableStateFlow<Resource<List<Genre>>> =
        MutableStateFlow(Resource.Loading)
    val genres get() = _genres

    fun getMovieGenres() = viewModelScope.launch {
        getMovieGenresUseCase().collectLatest {
            _genres.emit(it)
        }
    }

    fun getSerieGenres() = viewModelScope.launch {
        getSerieGenresUseCase().collectLatest {
            _genres.emit(it)
        }
    }
}