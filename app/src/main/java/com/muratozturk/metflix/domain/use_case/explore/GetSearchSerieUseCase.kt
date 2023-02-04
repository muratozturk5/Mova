package com.muratozturk.metflix.domain.use_case.explore

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSearchSerieUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(query: String, includeAdult: Boolean) =
        metflixRepository.getSearchSerie(query, includeAdult)
}