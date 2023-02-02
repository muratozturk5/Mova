package com.muratozturk.metflix.data.data_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muratozturk.metflix.common.Constants.NETWORK_PAGE_SIZE
import com.muratozturk.metflix.common.enums.MovieEnum
import com.muratozturk.metflix.common.enums.SerieEnum
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.Serie
import com.muratozturk.metflix.data.paging_source.MoviePagingSource
import com.muratozturk.metflix.data.paging_source.SeriePagingSource
import com.muratozturk.metflix.data.service.MetflixService
import com.muratozturk.metflix.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val metflixService: MetflixService) :
    DataSource.Remote {
    override suspend fun getPopularMovies(): MovieResponse = metflixService.getPopularMovies()
    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { MoviePagingSource(metflixService, MovieEnum.NOW_PLAYING_MOVIES) }
    ).flow

    override suspend fun getNowPlayingSeries(): Flow<PagingData<Serie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { SeriePagingSource(metflixService, SerieEnum.NOW_PLAYING_SERIES) }
    ).flow

    override suspend fun getDiscoverMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { MoviePagingSource(metflixService, MovieEnum.DISCOVER_MOVIES) }
    ).flow

    override suspend fun getDiscoverSeries(): Flow<PagingData<Serie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { SeriePagingSource(metflixService, SerieEnum.DISCOVER_SERIES) }
    ).flow

}