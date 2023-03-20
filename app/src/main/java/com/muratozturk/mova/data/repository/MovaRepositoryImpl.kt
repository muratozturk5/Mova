package com.muratozturk.mova.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.data.model.remote.genres.Genre
import com.muratozturk.mova.domain.mapper.*
import com.muratozturk.mova.domain.model.*
import com.muratozturk.mova.domain.repository.MovaRepository
import com.muratozturk.mova.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovaRepositoryImpl @Inject constructor(
    private val remote: DataSource.Remote,
    private val local: DataSource.Local,
    private val preference: DataSource.Preference
) :
    MovaRepository {
    override fun getPopularMovies(): Flow<Resource<List<MovieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPopularMovies().results.toMovieUI()

            response.forEach {
                it.isBookmarked = local.isBookmarked(it.id)
            }

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

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailsUI>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getMovieDetails(movieId).toMovieDetailsUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getMovieCredits(movieId: Int): Flow<Resource<List<CastUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getMovieCredits(movieId).cast.map { it.toCastUI() }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSerieDetails(serieId: Int): Flow<Resource<SerieDetailsUI>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getSerieDetails(serieId).toSerieDetailsUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSerieCredits(serieId: Int): Flow<Resource<List<CastUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getSerieCredits(serieId).cast.map { it.toCastUI() }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getMovieTrailers(movieId: Int): Flow<Resource<List<VideoUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getMovieTrailers(movieId).results.toVideoUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSerieTrailers(serieId: Int): Flow<Resource<List<VideoUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getSerieTrailers(serieId).results.toVideoUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getMovieImages(movieId: Int): Flow<Resource<List<ImageUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getMovieImages(movieId).backdrops.toImageUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSerieImages(serieId: Int): Flow<Resource<List<ImageUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getSerieImages(serieId).backdrops.toImageUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>> = flow {
        remote.getSimilarMovies(movieId).map { pagingData ->
            pagingData.map { it.toMovieUI() }
        }.collect { emit(it) }
    }

    override fun getSimilarSeries(serieId: Int): Flow<PagingData<SerieUI>> = flow {
        remote.getSimilarSeries(serieId).map { pagingData ->
            pagingData.map { it.toSerieUI() }
        }.collect { emit(it) }
    }

    override fun getPersonDetails(personId: Int): Flow<Resource<PersonDetailsUI>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPersonDetails(personId).toPersonDetailUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getPersonImages(personId: Int): Flow<Resource<List<ImageUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPersonImages(personId).profiles.toPersonImagesUI()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getPersonMovieCredits(personId: Int): Flow<Resource<List<MovieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response =
                remote.getPersonMovieCredits(personId).toPersonMovieUI()
                    .sortedByDescending { it.releaseDate }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getPersonSerieCredits(personId: Int): Flow<Resource<List<SerieUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getPersonSerieCredits(personId).toPersonSerieUI()
                .sortedByDescending { it.firstAirDate }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getLanguages(): Flow<Resource<List<LanguageUI>>> = flow {
        emit(Resource.Loading)
        try {
            val response =
                remote.getLanguages().toLanguageUI().sortedBy { it.englishName }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun addBookmark(bookmark: Bookmark) {
//        download image and save to local storage

//        val imagePath = bookmark.id.toString() + bookmark.name + ".png"
//        bookmark.imageFilePath = app.imageDownloadSaveFile(
//            imagePath,
//            Constants.getPosterPath(bookmark.poster)
//        )
        local.addBookmark(bookmark)
    }

    override suspend fun removeBookmark(id: Int) = local.removeBookmark(id)
    override fun isBookmarked(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.isBookmarked(id)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getBookmarks(): Flow<Resource<List<Bookmark>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getBookmarks()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun addDownload(download: Download) = local.addDownload(download)

    override suspend fun removeDownloaded(id: Int) = local.removeDownloaded(id)

    override fun isDownloaded(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.isDownloaded(id)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getDownloads(): Flow<Resource<List<Download>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getDownloads()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun setDarkMode(isDarkMode: Boolean) = preference.setDarkMode(isDarkMode)

    override fun getDarkMode(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getDarkMode()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getCurrentLanguage(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getCurrentLanguage()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun setCurrentLanguage(languageCode: String) =
        preference.setCurrentLanguage(languageCode)

    override fun getCurrentLanguageCode(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getCurrentLanguageCode()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun setCurrentLanguageCode(languageCode: String) =
        preference.setCurrentLanguageCode(languageCode)
}