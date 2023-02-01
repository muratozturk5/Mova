package com.muratozturk.metflix.domain.source

import androidx.paging.PagingData
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {
    interface Remote {
        suspend fun getPopularMovies(): MovieResponse
        suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>
        suspend fun getNowPlayingSeries(): SerieResponse
    }
}