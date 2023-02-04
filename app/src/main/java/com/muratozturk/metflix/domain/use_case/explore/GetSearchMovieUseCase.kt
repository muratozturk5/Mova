package com.muratozturk.metflix.domain.use_case.explore

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(query: String, includeAdult: Boolean) =
        metflixRepository.getSearchMovie(query, includeAdult)
}