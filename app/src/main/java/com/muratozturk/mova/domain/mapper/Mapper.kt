package com.muratozturk.mova.domain.mapper

import com.muratozturk.mova.data.model.remote.configurations.LanguagesResponse
import com.muratozturk.mova.data.model.remote.credits.Cast
import com.muratozturk.mova.data.model.remote.details.images.Backdrop
import com.muratozturk.mova.data.model.remote.details.movie.MovieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.serie.SerieDetailsResponse
import com.muratozturk.mova.data.model.remote.details.videos.Video
import com.muratozturk.mova.data.model.remote.movies.Movie
import com.muratozturk.mova.data.model.remote.person.PersonDetailResponse
import com.muratozturk.mova.data.model.remote.person.images.Profile
import com.muratozturk.mova.data.model.remote.person.movies.PersonMovieCreditsResponse
import com.muratozturk.mova.data.model.remote.person.series.PersonSerieCreditsResponse
import com.muratozturk.mova.data.model.remote.series.Serie
import com.muratozturk.mova.domain.model.*

fun List<Movie>.toMovieUI() = map {
    MovieUI(
        id = it.id,
        title = it.title,
        posterPath = it.posterPath,
        backdropPath = it.backdropPath,
        voteAverage = it.voteAverage,
        originalTitle = it.originalTitle,
        adult = it.adult,
        releaseDate = it.releaseDate
    )
}

fun Movie.toMovieUI() =
    MovieUI(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        adult = adult,
        releaseDate = releaseDate
    )

fun Serie.toSerieUI() =
    SerieUI(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        originalName = originalName,
        firstAirDate = firstAirDate
    )

fun MovieDetailsResponse.toMovieDetailsUI() =
    MovieDetailsUI(
        backdropPath = backdropPath,
        genres = genres,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        homepage = homepage
    )

fun Cast.toCastUI() =
    CastUI(
        id = id,
        name = name,
        originalName = originalName,
        character = character,
        profilePath = profilePath
    )

fun SerieDetailsResponse.toSerieDetailsUI() =
    SerieDetailsUI(
        backdropPath = backdropPath,
        genres = genres,
        id = id,
        name = name,
        firstAirDate = firstAirDate,
        numberOfSeasons = numberOfSeasons,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        posterPath = posterPath,
        tagline = tagline,
        voteAverage = voteAverage,
        homepage = homepage
    )

fun List<Video>.toVideoUI() = map {
    VideoUI(
        id = it.id,
        key = it.key,
        name = it.name,
        publishedAt = it.publishedAt
    )
}

fun List<Backdrop>.toImageUI() = map {
    ImageUI(
        filePath = it.filePath,
        voteCount = it.voteCount
    )
}

fun PersonDetailResponse.toPersonDetailUI() =
    PersonDetailsUI(
        alsoKnownAs = alsoKnownAs,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        gender = gender,
        homepage = homepage,
        id = id,
        imdbİd = imdbİd,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath
    )

fun List<Profile>.toPersonImagesUI() = map {
    ImageUI(
        filePath = it.filePath,
        voteCount = it.voteCount
    )
}

fun PersonMovieCreditsResponse.toPersonMovieUI() = cast.map {
    MovieUI(
        id = it.id,
        title = it.title,
        posterPath = it.posterPath,
        backdropPath = it.backdropPath,
        voteAverage = it.voteAverage,
        originalTitle = it.originalTitle,
        adult = it.adult,
        releaseDate = it.releaseDate
    )
}

fun PersonSerieCreditsResponse.toPersonSerieUI() = cast.map {
    SerieUI(
        id = it.id,
        name = it.name,
        posterPath = it.posterPath,
        backdropPath = it.backdropPath,
        voteAverage = it.voteAverage,
        originalName = it.originalName,
        firstAirDate = it.firstAirDate
    )
}

fun LanguagesResponse.toLanguageUI() = map {
    LanguageUI(
        englishName = it.englishName,
        iso6391 = it.iso6391,
        name = it.name
    )
}
