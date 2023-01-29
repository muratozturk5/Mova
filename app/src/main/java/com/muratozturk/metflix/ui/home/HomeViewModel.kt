package com.muratozturk.metflix.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.use_case.home.GetNowPlayingMoviesUseCase
import com.muratozturk.metflix.domain.use_case.home.GetNowPlayingSeriesUseCase
import com.muratozturk.metflix.domain.use_case.home.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getNowPlayingSeriesUseCase: GetNowPlayingSeriesUseCase
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<Resource<List<MovieUI>>>(Resource.Loading)
    val popularMovies
        get() = _popularMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<Resource<List<MovieUI>>>(Resource.Loading)
    val nowPlayingMovies
        get() = _nowPlayingMovies.asStateFlow()

    private val _nowPlayingSeries = MutableStateFlow<Resource<List<SerieUI>>>(Resource.Loading)
    val nowPlayingSeries
        get() = _nowPlayingSeries.asStateFlow()


    init {
        getPopularMovies()
        getNowPlayingMovies()
        getNowPlayingSeries()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        getPopularMoviesUseCase().collect {
            _popularMovies.emit(it)
            Timber.d("popular movies: $it")
        }
    }

    private fun getNowPlayingMovies() = viewModelScope.launch {
        getNowPlayingMoviesUseCase().collect {
            _nowPlayingMovies.emit(it)
            Timber.d("now playing movies: $it")
        }
    }

    private fun getNowPlayingSeries() = viewModelScope.launch {
        getNowPlayingSeriesUseCase().collect {
            _nowPlayingSeries.emit(it)
            Timber.d("now playing series: $it")
        }
    }
}