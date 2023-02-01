package com.muratozturk.metflix.ui.home.now_playing_series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.use_case.home.GetNowPlayingSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NowPlayingSeriesViewModel @Inject constructor(private val getNowPlayingSeriesUseCase: GetNowPlayingSeriesUseCase) :
    ViewModel() {

    private val _nowPlayingSeries = MutableStateFlow<Resource<List<SerieUI>>>(Resource.Loading)
    val nowPlayingSeries
        get() = _nowPlayingSeries.asStateFlow()

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() = viewModelScope.launch {
        getNowPlayingSeriesUseCase().collectLatest {
            _nowPlayingSeries.emit(it)
            Timber.d("now playing movies: $it")
        }
    }

}