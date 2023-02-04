package com.muratozturk.metflix.domain.use_case.explore

import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetDiscoverSeriesUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(filterResult: FilterResult?) =
        metflixRepository.getDiscoverSeries(filterResult)
}