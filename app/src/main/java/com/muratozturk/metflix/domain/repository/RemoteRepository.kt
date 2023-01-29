package com.muratozturk.metflix.domain.repository

import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getPopularMovies(): Flow<Resource<List<MovieUI>>>
    fun getNowPlayingMovies(): Flow<Resource<List<MovieUI>>>
    fun getNowPlayingSeries(): Flow<Resource<List<SerieUI>>>
}