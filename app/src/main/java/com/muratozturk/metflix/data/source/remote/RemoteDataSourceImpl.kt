package com.muratozturk.metflix.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muratozturk.metflix.common.Constants.NETWORK_PAGE_SIZE
import com.muratozturk.metflix.common.MovieRequestOptionsMapper
import com.muratozturk.metflix.common.enums.MovieEnum
import com.muratozturk.metflix.common.enums.SerieEnum
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.data.model.remote.credits.CreditsResponse
import com.muratozturk.metflix.data.model.remote.details.images.ImagesResponse
import com.muratozturk.metflix.data.model.remote.details.movie.MovieDetailsResponse
import com.muratozturk.metflix.data.model.remote.details.serie.SerieDetailsResponse
import com.muratozturk.metflix.data.model.remote.details.videos.VideosResponse
import com.muratozturk.metflix.data.model.remote.genres.GenresResponse
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.movies.MoviesResponse
import com.muratozturk.metflix.data.model.remote.person.PersonDetailResponse
import com.muratozturk.metflix.data.model.remote.person.images.PersonImagesResponse
import com.muratozturk.metflix.data.model.remote.person.movies.PersonMovieCreditsResponse
import com.muratozturk.metflix.data.model.remote.person.series.PersonSerieCreditsResponse
import com.muratozturk.metflix.data.model.remote.series.Serie
import com.muratozturk.metflix.data.paging_source.MoviePagingSource
import com.muratozturk.metflix.data.paging_source.SeriePagingSource
import com.muratozturk.metflix.domain.source.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val metflixService: MetflixService,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper
) :
    DataSource.Remote {
    override suspend fun getPopularMovies(): MoviesResponse = metflixService.getPopularMovies()
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
                metflixService,
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
                metflixService, SerieEnum.NOW_PLAYING_SERIES,
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
                    metflixService,
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
                    metflixService, SerieEnum.DISCOVER_SERIES,
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
                metflixService,
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
                metflixService, SerieEnum.SEARCH_SERIES, query,
                movieRequestOptionsMapper = movieRequestOptionsMapper,
                includeAdult = includeAdult
            )
        }
    ).flow

    override suspend fun getMovieGenres(): GenresResponse = metflixService.getMovieGenres()

    override suspend fun getSerieGenres(): GenresResponse = metflixService.getSerieGenres()
    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse =
        metflixService.getMovieDetails(movieId)

    override suspend fun getMovieCredits(movieId: Int): CreditsResponse =
        metflixService.getMovieCredits(movieId)

    override suspend fun getSerieDetails(serieId: Int): SerieDetailsResponse =
        metflixService.getSerieDetails(serieId)

    override suspend fun getSerieCredits(serieId: Int): CreditsResponse =
        metflixService.getSerieCredits(serieId)

    override suspend fun getMovieTrailers(movieId: Int): VideosResponse =
        metflixService.getMovieTrailers(movieId)

    override suspend fun getSerieTrailers(serieId: Int): VideosResponse =
        metflixService.getSerieTrailers(serieId)

    override suspend fun getMovieImages(movieId: Int): ImagesResponse =
        metflixService.getMovieImages(movieId)

    override suspend fun getSerieImages(serieId: Int): ImagesResponse =
        metflixService.getSerieImages(serieId)

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
                metflixService, MovieEnum.SIMILAR_MOVIES,
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
                metflixService, SerieEnum.SIMILAR_SERIES,
                serieId = serieId,
                movieRequestOptionsMapper = movieRequestOptionsMapper
            )
        }
    ).flow

    override suspend fun getPersonDetails(personId: Int): PersonDetailResponse =
        metflixService.getPeopleDetails(personId)

    override suspend fun getPersonImages(personId: Int): PersonImagesResponse =
        metflixService.getPersonImages(personId)

    override suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse =
        metflixService.getPersonMovieCredits(personId)

    override suspend fun getPersonSerieCredits(personId: Int): PersonSerieCreditsResponse =
        metflixService.getPersonSerieCredits(personId)


}