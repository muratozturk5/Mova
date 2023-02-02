package com.muratozturk.metflix.data.service

import com.muratozturk.metflix.common.Constants
import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MetflixService {

    @GET(Constants.Endpoints.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(): MovieResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MovieResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_SERIES)
    suspend fun getNowPlayingSeries(@Query("page") page: Int): SerieResponse

}