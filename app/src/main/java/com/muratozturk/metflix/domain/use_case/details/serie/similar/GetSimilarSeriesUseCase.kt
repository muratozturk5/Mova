package com.muratozturk.metflix.domain.use_case.details.serie.similar

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSimilarSeriesUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(serieId: Int) = metflixRepository.getSimilarSeries(serieId)
}