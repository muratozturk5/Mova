package com.muratozturk.metflix.domain.use_case.details.movie.similar

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(movieId: Int) = metflixRepository.getSimilarMovies(movieId)
}