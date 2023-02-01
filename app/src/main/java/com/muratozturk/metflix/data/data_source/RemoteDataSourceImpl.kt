package com.muratozturk.metflix.data.data_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muratozturk.metflix.common.Constants.NETWORK_PAGE_SIZE
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse
import com.muratozturk.metflix.data.paging_source.PagingSource
import com.muratozturk.metflix.data.service.MovieService
import com.muratozturk.metflix.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieService: MovieService) :
    DataSource.Remote {
    override suspend fun getPopularMovies(): MovieResponse = movieService.getPopularMovies()
    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { PagingSource(movieService) }
    ).flow

    override suspend fun getNowPlayingSeries(): SerieResponse = movieService.getNowPlayingSeries()

}