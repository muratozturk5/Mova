package com.muratozturk.metflix.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.data.model.remote.genres.Genre
import com.muratozturk.metflix.domain.mapper.toMovieUI
import com.muratozturk.metflix.domain.mapper.toSerieUI
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.repository.MetflixRepository
import com.muratozturk.metflix.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MetflixRepositoryImpl @Inject constructor(private val remote: DataSource.Remote) :
    MetflixRepository {
    override fun getPopularMovies(): Flow<Resource<List<MovieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPopularMovies().results.toMovieUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getNowPlayingMovies(): Flow<PagingData<MovieUI>> = flow {
        remote.getNowPlayingMovies().map { pagingData ->
            pagingData.map { it.toMovieUI() }
        }.collect {
            emit(it)
        }
    }

    override fun getNowPlayingSeries(): Flow<PagingData<SerieUI>> = flow {
        remote.getNowPlayingSeries().map { pagingData ->
            pagingData.map { it.toSerieUI() }
        }.collect { emit(it) }
    }

    override fun getDiscoverMovies(filterResult: FilterResult?): Flow<PagingData<MovieUI>> = flow {
        remote.getDiscoverMovies(filterResult).map { pagingData ->
            pagingData.map { it.toMovieUI() }
        }.collect { emit(it) }
    }

    override fun getDiscoverSeries(filterResult: FilterResult?): Flow<PagingData<SerieUI>> = flow {
        remote.getDiscoverSeries(filterResult).map { pagingData ->
            pagingData.map { it.toSerieUI() }
        }.collect { emit(it) }
    }

    override fun getSearchMovie(query: String, includeAdult: Boolean): Flow<PagingData<MovieUI>> =
        flow {
            remote.getSearchMovie(query, includeAdult).map { pagingData ->
                pagingData.map { it.toMovieUI() }
            }.collect { emit(it) }
        }

    override fun getSearchSerie(query: String, includeAdult: Boolean): Flow<PagingData<SerieUI>> =
        flow {
            remote.getSearchSerie(query, includeAdult).map { pagingData ->
                pagingData.map { it.toSerieUI() }
            }.collect { emit(it) }
        }

    override fun getMovieGenres(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(remote.getMovieGenres().genres))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSerieGenres(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(remote.getSerieGenres().genres))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}