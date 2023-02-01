package com.muratozturk.metflix.domain.use_case.home

import com.muratozturk.metflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke() = movieRepository.getNowPlayingMovies()
}
