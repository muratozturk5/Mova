package com.muratozturk.metflix.domain.repository

import androidx.paging.PagingData
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Resource<List<MovieUI>>>
    fun getNowPlayingMovies(): Flow<PagingData<MovieUI>>
    fun getNowPlayingSeries(): Flow<Resource<List<SerieUI>>>
}