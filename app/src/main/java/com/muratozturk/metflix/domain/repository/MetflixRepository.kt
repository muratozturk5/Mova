package com.muratozturk.metflix.domain.repository

import androidx.paging.PagingData
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.data.model.remote.genres.Genre
import com.muratozturk.metflix.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MetflixRepository {
    fun getPopularMovies(): Flow<Resource<List<MovieUI>>>
    fun getNowPlayingMovies(): Flow<PagingData<MovieUI>>
    fun getNowPlayingSeries(): Flow<PagingData<SerieUI>>
    fun getDiscoverMovies(filterResult: FilterResult?): Flow<PagingData<MovieUI>>
    fun getDiscoverSeries(filterResult: FilterResult?): Flow<PagingData<SerieUI>>
    fun getSearchMovie(query: String, includeAdult: Boolean): Flow<PagingData<MovieUI>>
    fun getSearchSerie(query: String, includeAdult: Boolean): Flow<PagingData<SerieUI>>
    fun getMovieGenres(): Flow<Resource<List<Genre>>>
    fun getSerieGenres(): Flow<Resource<List<Genre>>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailsUI>>
    fun getMovieCredits(movieId: Int): Flow<Resource<List<CastUI>>>
    fun getSerieDetails(serieId: Int): Flow<Resource<SerieDetailsUI>>
    fun getSerieCredits(serieId: Int): Flow<Resource<List<CastUI>>>
    fun getMovieTrailers(movieId: Int): Flow<Resource<List<VideoUI>>>
    fun getSerieTrailers(serieId: Int): Flow<Resource<List<VideoUI>>>
    fun getMovieImages(movieId: Int): Flow<Resource<List<ImageUI>>>
    fun getSerieImages(serieId: Int): Flow<Resource<List<ImageUI>>>
}