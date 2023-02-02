package com.muratozturk.metflix.domain.use_case.home

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: MetflixRepository) {
    operator fun invoke() = movieRepository.getPopularMovies()
}
