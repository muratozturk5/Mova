package com.muratozturk.metflix.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.use_case.explore.GetDiscoverMoviesUseCase
import com.muratozturk.metflix.domain.use_case.explore.GetDiscoverSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val getDiscoverSeriesUseCase: GetDiscoverSeriesUseCase
) : ViewModel() {

    private val _discoverMovies: MutableStateFlow<PagingData<MovieUI>> =
        MutableStateFlow(PagingData.empty())
    val discoverMovies
        get() = _discoverMovies


    private val _discoverSeries: MutableStateFlow<PagingData<SerieUI>> =
        MutableStateFlow(PagingData.empty())
    val discoverSeries
        get() = _discoverSeries

    init {
        getDiscoverMovies()
        getDiscoverSeries()
    }

    private fun getDiscoverMovies() = viewModelScope.launch {
        getDiscoverMoviesUseCase().cachedIn(viewModelScope).collectLatest {
            _discoverMovies.emit(it)
        }
    }

    private fun getDiscoverSeries() = viewModelScope.launch {
        getDiscoverSeriesUseCase().cachedIn(viewModelScope).collectLatest {
            _discoverSeries.emit(it)
        }
    }
}