package com.muratozturk.mova.domain.repository

import androidx.paging.PagingData
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.data.model.remote.genres.Genre
import com.muratozturk.mova.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MovaRepository {
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
    fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>>
    fun getSimilarSeries(serieId: Int): Flow<PagingData<SerieUI>>
    fun getPersonDetails(personId: Int): Flow<Resource<PersonDetailsUI>>
    fun getPersonImages(personId: Int): Flow<Resource<List<ImageUI>>>
    fun getPersonMovieCredits(personId: Int): Flow<Resource<List<MovieUI>>>
    fun getPersonSerieCredits(personId: Int): Flow<Resource<List<SerieUI>>>
    fun getLanguages(): Flow<Resource<List<LanguageUI>>>


    suspend fun addBookmark(bookmark: Bookmark)
    suspend fun removeBookmark(id: Int)
    fun isBookmarked(id: Int): Flow<Resource<Boolean>>
    fun getBookmarks(): Flow<Resource<List<Bookmark>>>
    suspend fun addDownload(download: Download)
    suspend fun removeDownloaded(id: Int)
    fun isDownloaded(id: Int): Flow<Resource<Boolean>>
    fun getDownloads(): Flow<Resource<List<Download>>>


    fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Flow<Resource<Boolean>>
    fun getCurrentLanguage(): Flow<Resource<String>>
    fun setCurrentLanguage(languageCode: String)
    fun getCurrentLanguageCode(): Flow<Resource<String>>
    fun setCurrentLanguageCode(languageCode: String)


}