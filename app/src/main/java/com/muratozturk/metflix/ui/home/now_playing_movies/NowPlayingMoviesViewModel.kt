package com.muratozturk.metflix.ui.home.now_playing_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.use_case.home.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase) :
    ViewModel() {

    private val _nowPlayingMovies =
        MutableStateFlow<PagingData<MovieUI>>(PagingData.empty())
    val nowPlayingMovies
        get() = _nowPlayingMovies.asStateFlow()

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() = viewModelScope.launch {
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope).collectLatest {
            _nowPlayingMovies.emit(it)
            Timber.d("now playing movies: $it")
        }
    }

}