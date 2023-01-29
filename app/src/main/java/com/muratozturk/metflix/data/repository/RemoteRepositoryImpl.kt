package com.muratozturk.metflix.data.repository

import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.mapper.toMovieUI
import com.muratozturk.metflix.domain.mapper.toSerieUI
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.repository.RemoteRepository
import com.muratozturk.metflix.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val remote: DataSource.Remote) :
    RemoteRepository {
    override fun getPopularMovies(): Flow<Resource<List<MovieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPopularMovies().results.toMovieUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getNowPlayingMovies(): Flow<Resource<List<MovieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getNowPlayingMovies().results.toMovieUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getNowPlayingSeries(): Flow<Resource<List<SerieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getNowPlayingSeries().results.toSerieUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}