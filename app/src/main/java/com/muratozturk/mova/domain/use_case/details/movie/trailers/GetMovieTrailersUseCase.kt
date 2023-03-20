package com.muratozturk.mova.domain.use_case.details.movie.trailers

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(val repository: MovaRepository) {
    operator fun invoke(movieId: Int) = repository.getMovieTrailers(movieId)
}