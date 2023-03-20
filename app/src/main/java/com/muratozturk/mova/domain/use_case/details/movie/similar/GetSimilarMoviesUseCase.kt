package com.muratozturk.mova.domain.use_case.details.movie.similar

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(movieId: Int) = movaRepository.getSimilarMovies(movieId)
}