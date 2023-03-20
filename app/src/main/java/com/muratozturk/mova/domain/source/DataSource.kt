package com.muratozturk.mova.domain.source

import androidx.paging.PagingData
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.data.model.remote.configurations.LanguagesResponse
import com.muratozturk.mova.data.model.remote.credits.CreditsResponse
import com.muratozturk.mova.data.model.remote.details.images.ImagesResponse
import com.muratozturk.mova.data.model.remote.details.movie.MovieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.serie.SerieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.videos.VideosResponse
import com.muratozturk.mova.data.model.remote.genres.GenresResponse
import com.muratozturk.mova.data.model.remote.movies.Movie
import com.muratozturk.mova.data.model.remote.movies.MoviesResponse
import com.muratozturk.mova.data.model.remote.person.PersonDetailResponse
import com.muratozturk.mova.data.model.remote.person.images.PersonImagesResponse
import com.muratozturk.mova.data.model.remote.person.movies.PersonMovieCreditsResponse
import com.muratozturk.mova.data.model.remote.person.series.PersonSerieCreditsResponse
import com.muratozturk.mova.data.model.remote.series.Serie
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
        suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse
        suspend fun getMovieCredits(movieId: Int): CreditsResponse
        suspend fun getSerieDetails(serieId: Int): SerieDetailsResponse
        suspend fun getSerieCredits(serieId: Int): CreditsResponse
        suspend fun getMovieTrailers(movieId: Int): VideosResponse
        suspend fun getSerieTrailers(serieId: Int): VideosResponse
        suspend fun getMovieImages(movieId: Int): ImagesResponse
        suspend fun getSerieImages(serieId: Int): ImagesResponse
        suspend fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>>
        suspend fun getSimilarSeries(serieId: Int): Flow<PagingData<Serie>>
        suspend fun getPersonDetails(personId: Int): PersonDetailResponse
        suspend fun getPersonImages(personId: Int): PersonImagesResponse
        suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse
        suspend fun getPersonSerieCredits(personId: Int): PersonSerieCreditsResponse
        suspend fun getLanguages(): LanguagesResponse
    }

    interface Local {
        suspend fun addBookmark(bookmark: Bookmark)
        suspend fun removeBookmark(id: Int)
        suspend fun isBookmarked(id: Int): Boolean
        suspend fun getBookmarks(): List<Bookmark>
        suspend fun addDownload(download: Download)
        suspend fun removeDownloaded(id: Int)
        suspend fun isDownloaded(id: Int): Boolean
        suspend fun getDownloads(): List<Download>
    }

    interface Preference {
        fun setDarkMode(isDarkMode: Boolean)
        fun getDarkMode(): Boolean
        fun setCurrentLanguage(language: String)
        fun getCurrentLanguage(): String

        fun setCurrentLanguageCode(language: String)
        fun getCurrentLanguageCode(): String
    }

}