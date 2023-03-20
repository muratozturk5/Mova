package com.muratozturk.mova.data.source.remote

import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.data.model.remote.configurations.LanguagesResponse
import com.muratozturk.mova.data.model.remote.credits.CreditsResponse
import com.muratozturk.mova.data.model.remote.details.images.ImagesResponse
import com.muratozturk.mova.data.model.remote.details.movie.MovieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.serie.SerieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.videos.VideosResponse
import com.muratozturk.mova.data.model.remote.genres.GenresResponse
import com.muratozturk.mova.data.model.remote.movies.MoviesResponse
import com.muratozturk.mova.data.model.remote.person.PersonDetailResponse
import com.muratozturk.mova.data.model.remote.person.images.PersonImagesResponse
import com.muratozturk.mova.data.model.remote.person.movies.PersonMovieCreditsResponse
import com.muratozturk.mova.data.model.remote.person.series.PersonSerieCreditsResponse
import com.muratozturk.mova.data.model.remote.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovaService {

    @GET(Constants.Endpoints.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(): MoviesResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MoviesResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_SERIES)
    suspend fun getNowPlayingSeries(@Query("page") page: Int): SeriesResponse

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @QueryMap options: Map<String, String>
    ): MoviesResponse

    @GET(Constants.Endpoints.GET_DISCOVER_SERIES)
    suspend fun getDiscoverSeries(
        @Query("page") page: Int,
        @QueryMap options: Map<String, String>
    ): SeriesResponse

    @GET(Constants.Endpoints.GET_SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): MoviesResponse

    @GET(Constants.Endpoints.GET_SEARCH_SERIE)
    suspend fun getSearchSerie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): SeriesResponse

    @GET(Constants.Endpoints.GET_MOVIE_GENRES)
    suspend fun getMovieGenres(): GenresResponse

    @GET(Constants.Endpoints.GET_SERIE_GENRES)
    suspend fun getSerieGenres(): GenresResponse

    @GET(Constants.Endpoints.GET_MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResponse

    @GET(Constants.Endpoints.GET_MOVIE_CREDITS)
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): CreditsResponse

    @GET(Constants.Endpoints.GET_SERIE_DETAILS)
    suspend fun getSerieDetails(@Path("tv_id") serieId: Int): SerieDetailsResponse

    @GET(Constants.Endpoints.GET_SERIE_CREDITS)
    suspend fun getSerieCredits(@Path("tv_id") serieId: Int): CreditsResponse

    @GET(Constants.Endpoints.GET_MOVIE_TRAILERS)
    suspend fun getMovieTrailers(@Path("movie_id") movieId: Int): VideosResponse

    @GET(Constants.Endpoints.GET_SERIE_TRAILERS)
    suspend fun getSerieTrailers(@Path("tv_id") serieId: Int): VideosResponse

    @GET(Constants.Endpoints.GET_MOVIE_IMAGES)
    suspend fun getMovieImages(@Path("movie_id") movieId: Int): ImagesResponse

    @GET(Constants.Endpoints.GET_SERIE_IMAGES)
    suspend fun getSerieImages(@Path("tv_id") serieId: Int): ImagesResponse

    @GET(Constants.Endpoints.GET_MOVIE_SIMILAR)
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int, @Query("page") page: Int,
    ): MoviesResponse

    @GET(Constants.Endpoints.GET_SERIE_SIMILAR)
    suspend fun getSimilarSeries(
        @Path("tv_id") serieId: Int, @Query("page") page: Int,
    ): SeriesResponse

    @GET(Constants.Endpoints.GET_PERSON_DETAIL)
    suspend fun getPeopleDetails(@Path("person_id") personId: Int): PersonDetailResponse

    @GET(Constants.Endpoints.GET_PERSON_IMAGES)
    suspend fun getPersonImages(
        @Path("person_id") personId: Int
    ): PersonImagesResponse

    @GET(Constants.Endpoints.GET_PERSON_MOVIE_CREDITS)
    suspend fun getPersonMovieCredits(@Path("person_id") personId: Int): PersonMovieCreditsResponse

    @GET(Constants.Endpoints.GET_PERSON_SERIE_CREDITS)
    suspend fun getPersonSerieCredits(@Path("person_id") personId: Int): PersonSerieCreditsResponse

    @GET(Constants.Endpoints.GET_LANGUAGES)
    suspend fun getLanguages(): LanguagesResponse

}