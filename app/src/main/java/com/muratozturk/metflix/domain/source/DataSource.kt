package com.muratozturk.metflix.domain.source

import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse

interface DataSource {
    interface Remote {
        suspend fun getPopularMovies(): MovieResponse
        suspend fun getNowPlayingMovies(): MovieResponse
        suspend fun getNowPlayingSeries(): SerieResponse
    }
}