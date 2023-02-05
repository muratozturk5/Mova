package com.muratozturk.metflix.domain.use_case.details.movie

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(movieId: Int) = metflixRepository.getMovieCredits(movieId)
}