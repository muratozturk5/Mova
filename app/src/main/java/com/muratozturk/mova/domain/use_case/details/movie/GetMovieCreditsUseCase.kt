package com.muratozturk.mova.domain.use_case.details.movie

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(movieId: Int) = movaRepository.getMovieCredits(movieId)
}