package com.muratozturk.metflix.domain.mapper

import com.muratozturk.metflix.data.model.remote.credits.Cast
import com.muratozturk.metflix.data.model.remote.details.images.Backdrop
import com.muratozturk.metflix.data.model.remote.details.movie.MovieDetailsResponse
import com.muratozturk.metflix.data.model.remote.details.serie.SerieDetailsResponse
import com.muratozturk.metflix.data.model.remote.details.videos.Video
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.series.Serie
import com.muratozturk.metflix.domain.model.*

fun List<Movie>.toMovieUI() = map {
    MovieUI(
        id = it.id,
        title = it.title,
        posterPath = it.posterPath,
        backdropPath = it.backdropPath,
        voteAverage = it.voteAverage,
        originalTitle = it.originalTitle,
        adult = it.adult
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
        adult = adult
    )

fun Serie.toSerieUI() =
    SerieUI(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        originalName = originalName
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
        voteAverage = voteAverage
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
        voteAverage = voteAverage
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