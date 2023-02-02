package com.muratozturk.metflix.common

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "2d8356343a8ebcb5b324c3fcffea5706"
    private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/original"
    const val NETWORK_PAGE_SIZE = 10
    const val STARTING_PAGE = 1

    object Authentication {
        const val WEB_CLIENT_ID =
            "824409577602-6qimbh36qk4hbup0q1pdpdqurprdb2a3.apps.googleusercontent.com"
        const val REQ_SIGN_IN_GOOGLE = -1
    }

    object Endpoints {
        const val GET_POPULAR_MOVIES = "movie/popular"
        const val GET_NOW_PLAYING_MOVIES = "movie/now_playing"
        const val GET_NOW_PLAYING_SERIES = "tv/on_the_air"
        const val GET_DISCOVER_MOVIES = "discover/movie"
        const val GET_DISCOVER_SERIES = "discover/tv"
    }

    fun getPosterPath(posterPath: String?): String {
        return BASE_URL_IMAGE + posterPath
    }

    fun getBackDropPath(backDropPath: String?): String {
        return BASE_URL_IMAGE + backDropPath
    }
}