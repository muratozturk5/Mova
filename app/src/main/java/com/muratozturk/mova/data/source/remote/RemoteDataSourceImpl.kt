package com.muratozturk.mova.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muratozturk.mova.common.Constants.NETWORK_PAGE_SIZE
import com.muratozturk.mova.common.MovieRequestOptionsMapper
import com.muratozturk.mova.common.enums.MovieEnum
import com.muratozturk.mova.common.enums.SerieEnum
import com.muratozturk.mova.data.model.FilterResult
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
import com.muratozturk.mova.data.paging_source.MoviePagingSource
import com.muratozturk.mova.data.paging_source.SeriePagingSource
import com.muratozturk.mova.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val movaService: MovaService,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper
) :
    DataSource.Remote {
    override suspend fun getPopularMovies(): MoviesResponse = movaService.getPopularMovies()
    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(
                movaService,
                MovieEnum.NOW_PLAYING_MOVIES,
                movieRequestOptionsMapper = movieRequestOptionsMapper
            )
        }
    ).flow

    override suspend fun getNowPlayingSeries(): Flow<PagingData<Serie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            SeriePagingSource(
                movaService, SerieEnum.NOW_PLAYING_SERIES,
                movieRequestOptionsMapper = movieRequestOptionsMapper
            )
        }
    ).flow

    override suspend fun getDiscoverMovies(filterResult: FilterResult?): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movaService,
                    MovieEnum.DISCOVER_MOVIES,
                    movieRequestOptionsMapper = movieRequestOptionsMapper,
                    filterResult = filterResult
                )
            }
        ).flow

    override suspend fun getDiscoverSeries(filterResult: FilterResult?): Flow<PagingData<Serie>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SeriePagingSource(
                    movaService, SerieEnum.DISCOVER_SERIES,
                    movieRequestOptionsMapper = movieRequestOptionsMapper,
                    filterResult = filterResult
                )
            }
        ).flow

    override suspend fun getSearchMovie(
        query: String,
        includeAdult: Boolean
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(
                movaService,
                MovieEnum.SEARCH_MOVIES,
                query,
                movieRequestOptionsMapper = movieRequestOptionsMapper,
                includeAdult = includeAdult
            )
        }
    ).flow

    override suspend fun getSearchSerie(
        query: String,
        includeAdult: Boolean
    ): Flow<PagingData<Serie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            SeriePagingSource(
                movaService, SerieEnum.SEARCH_SERIES, query,
                movieRequestOptionsMapper = movieRequestOptionsMapper,
                includeAdult = includeAdult
            )
        }
    ).flow

    override suspend fun getMovieGenres(): GenresResponse = movaService.getMovieGenres()

    override suspend fun getSerieGenres(): GenresResponse = movaService.getSerieGenres()
    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse =
        movaService.getMovieDetails(movieId)

    override suspend fun getMovieCredits(movieId: Int): CreditsResponse =
        movaService.getMovieCredits(movieId)

    override suspend fun getSerieDetails(serieId: Int): SerieDetailsResponse =
        movaService.getSerieDetails(serieId)

    override suspend fun getSerieCredits(serieId: Int): CreditsResponse =
        movaService.getSerieCredits(serieId)

    override suspend fun getMovieTrailers(movieId: Int): VideosResponse =
        movaService.getMovieTrailers(movieId)

    override suspend fun getSerieTrailers(serieId: Int): VideosResponse =
        movaService.getSerieTrailers(serieId)

    override suspend fun getMovieImages(movieId: Int): ImagesResponse =
        movaService.getMovieImages(movieId)

    override suspend fun getSerieImages(serieId: Int): ImagesResponse =
        movaService.getSerieImages(serieId)

    override suspend fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(
                movaService, MovieEnum.SIMILAR_MOVIES,
                movieId = movieId,
                movieRequestOptionsMapper = movieRequestOptionsMapper
            )
        }
    ).flow

    override suspend fun getSimilarSeries(
        serieId: Int
    ): Flow<PagingData<Serie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = 2,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            SeriePagingSource(
                movaService, SerieEnum.SIMILAR_SERIES,
                serieId = serieId,
                movieRequestOptionsMapper = movieRequestOptionsMapper
            )
        }
    ).flow

    override suspend fun getPersonDetails(personId: Int): PersonDetailResponse =
        movaService.getPeopleDetails(personId)

    override suspend fun getPersonImages(personId: Int): PersonImagesResponse =
        movaService.getPersonImages(personId)

    override suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse =
        movaService.getPersonMovieCredits(personId)

    override suspend fun getPersonSerieCredits(personId: Int): PersonSerieCreditsResponse =
        movaService.getPersonSerieCredits(personId)

    override suspend fun getLanguages(): LanguagesResponse = movaService.getLanguages()


}