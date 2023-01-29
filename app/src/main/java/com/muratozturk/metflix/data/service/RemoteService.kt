package com.muratozturk.metflix.data.service

import com.muratozturk.metflix.common.Constants
import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse
import retrofit2.http.GET

interface RemoteService {

    @GET(Constants.Endpoints.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(): MovieResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET(Constants.Endpoints.GET_NOW_PLAYING_SERIES)
    suspend fun getNowPlayingSeries(): SerieResponse

}