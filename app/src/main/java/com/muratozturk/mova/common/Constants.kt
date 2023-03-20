package com.muratozturk.mova.common

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "2d8356343a8ebcb5b324c3fcffea5706"
    private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/original"
    private const val BASE_URL_IMAGE_YOUTUBE = "https://img.youtube.com/vi/"
    private const val SIZE_IMG_YOUTUBE = "/hq720.jpg"
    const val NETWORK_PAGE_SIZE = 10
    const val STARTING_PAGE = 1


    object Preferences {
        const val LANGUAGE_CODE = "languageCode"
        const val LANGUAGE_NAME = "languageName"
        const val SHARED_PREF_NAME = "mova_shared_pref"
        const val DARK_MODE = "darkMode"
    }

    object Arguments {
        const val ID = "id"
        const val MEDIA_TYPE = "mediaType"
        const val FILTER_RESULT = "filterResult"
        const val POP_UP = "popUp"
        const val DELETED_POSITION = "deletedPosition"

    }

    object Filter {
        const val SORT_BY = "sort_by"
        const val INCLUDE_ADULT = "include_adult"
        const val WITH_GENRES = "with_genres"
    }


    object SortBy {
        const val POPULARITY = "popularity"
        const val VOTE_COUNT = "vote_count"
        const val VOTE_AVERAGE = "vote_average"
        const val RELEASE_DATE = "release_date"
    }

    object Authentication {
        const val WEB_CLIENT_ID =
            "824409577602-6qimbh36qk4hbup0q1pdpdqurprdb2a3.apps.googleusercontent.com"
        const val REQ_SIGN_IN_GOOGLE = -1
    }

    object Queries {
        const val DELETE_BOOKMARK = "DELETE FROM bookmarks WHERE id = :id"
        const val IS_BOOKMARKED = "SELECT EXISTS(SELECT * FROM bookmarks WHERE id = :id)"
        const val GET_BOOKMARKS = "SELECT * FROM bookmarks ORDER BY addDate DESC"
        const val DELETE_DOWNLOAD = "DELETE FROM downloads WHERE id = :id"
        const val IS_DOWNLOADED = "SELECT EXISTS(SELECT * FROM downloads WHERE id = :id)"
        const val GET_DOWNLOADS = "SELECT * FROM downloads ORDER BY addDate DESC"
    }

    object Endpoints {
        const val GET_POPULAR_MOVIES = "movie/popular"
        const val GET_NOW_PLAYING_MOVIES = "movie/now_playing"
        const val GET_NOW_PLAYING_SERIES = "tv/on_the_air"
        const val GET_DISCOVER_MOVIES = "discover/movie"
        const val GET_DISCOVER_SERIES = "discover/tv"
        const val GET_SEARCH_MOVIE = "search/movie"
        const val GET_SEARCH_SERIE = "search/tv"
        const val GET_MOVIE_GENRES = "genre/movie/list"
        const val GET_SERIE_GENRES = "genre/tv/list"
        const val GET_MOVIE_DETAILS = "movie/{movie_id}"
        const val GET_MOVIE_CREDITS = "movie/{movie_id}/credits"
        const val GET_SERIE_DETAILS = "tv/{tv_id}"
        const val GET_SERIE_CREDITS = "tv/{tv_id}/credits"
        const val GET_MOVIE_TRAILERS = "movie/{movie_id}/videos"
        const val GET_SERIE_TRAILERS = "tv/{tv_id}/videos"
        const val GET_MOVIE_IMAGES = "movie/{movie_id}/images"
        const val GET_SERIE_IMAGES = "tv/{tv_id}/images"
        const val GET_MOVIE_SIMILAR = "movie/{movie_id}/similar"
        const val GET_SERIE_SIMILAR = "tv/{tv_id}/similar"
        const val GET_PERSON_DETAIL = "person/{person_id}"
        const val GET_PERSON_IMAGES = "person/{person_id}/images"
        const val GET_PERSON_MOVIE_CREDITS = "person/{person_id}/movie_credits"
        const val GET_PERSON_SERIE_CREDITS = "person/{person_id}/tv_credits"
        const val GET_LANGUAGES = "configuration/languages"

    }

    fun getPosterPath(posterPath: String?): String {
        return BASE_URL_IMAGE + posterPath
    }

    fun getBackDropPath(backDropPath: String?): String {
        return BASE_URL_IMAGE + backDropPath
    }

    fun getYouTubePath(youTubePath: String?): String {
        return BASE_URL_IMAGE_YOUTUBE + youTubePath + SIZE_IMG_YOUTUBE
    }

    fun getFlagPath(iso6391: String): String {
        return "https://www.unknown.nu/flags/images/$iso6391-100"
    }
}