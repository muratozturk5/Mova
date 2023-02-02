package com.muratozturk.metflix.domain.mapper

import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.model.remote.series.Serie
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI

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


fun List<Serie>.toSerieUI() = map {
    SerieUI(
        id = it.id,
        name = it.name,
        posterPath = it.posterPath,
        backdropPath = it.backdropPath,
        voteAverage = it.voteAverage,
        originalName = it.originalName
    )
}