package com.muratozturk.mova.domain.use_case.explore

import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetDiscoverSeriesUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(filterResult: FilterResult?) =
        movaRepository.getDiscoverSeries(filterResult)
}