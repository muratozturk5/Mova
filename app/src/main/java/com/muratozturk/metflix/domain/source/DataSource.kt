package com.muratozturk.metflix.domain.source

import androidx.paging.PagingData
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.data.model.remote.genres.GenresResponse
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.movies.MoviesResponse
import com.muratozturk.metflix.data.model.remote.series.Serie
import kotlinx.coroutines.flow.Flow

interface DataSource {

    interface Remote {
        suspend fun getPopularMovies(): MoviesResponse
        suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>
        suspend fun getNowPlayingSeries(): Flow<PagingData<Serie>>
        suspend fun getDiscoverMovies(filterResult: FilterResult?): Flow<PagingData<Movie>>
        suspend fun getDiscoverSeries(filterResult: FilterResult?): Flow<PagingData<Serie>>
        suspend fun getSearchMovie(query: String, includeAdult: Boolean): Flow<PagingData<Movie>>
        suspend fun getSearchSerie(query: String, includeAdult: Boolean): Flow<PagingData<Serie>>
        suspend fun getMovieGenres(): GenresResponse
        suspend fun getSerieGenres(): GenresResponse
    }

}