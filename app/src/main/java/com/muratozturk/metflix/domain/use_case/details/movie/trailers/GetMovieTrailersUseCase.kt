package com.muratozturk.metflix.domain.use_case.details.movie.trailers

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(val repository: MetflixRepository) {
    operator fun invoke(movieId: Int) = repository.getMovieTrailers(movieId)
}