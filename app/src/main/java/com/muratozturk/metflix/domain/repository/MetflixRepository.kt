package com.muratozturk.metflix.domain.repository

import androidx.paging.PagingData
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import kotlinx.coroutines.flow.Flow

interface MetflixRepository {
    fun getPopularMovies(): Flow<Resource<List<MovieUI>>>
    fun getNowPlayingMovies(): Flow<PagingData<MovieUI>>
    fun getNowPlayingSeries(): Flow<PagingData<SerieUI>>
    fun getDiscoverMovies(): Flow<PagingData<MovieUI>>
    fun getDiscoverSeries(): Flow<PagingData<SerieUI>>
}