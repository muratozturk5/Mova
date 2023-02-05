package com.muratozturk.metflix.domain.use_case.details.movie

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MetflixRepository
) {
    operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}