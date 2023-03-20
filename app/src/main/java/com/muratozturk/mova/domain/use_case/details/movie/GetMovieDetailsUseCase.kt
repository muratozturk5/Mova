package com.muratozturk.mova.domain.use_case.details.movie

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovaRepository
) {
    operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}