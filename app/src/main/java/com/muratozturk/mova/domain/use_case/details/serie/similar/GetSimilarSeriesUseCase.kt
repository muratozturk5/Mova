package com.muratozturk.mova.domain.use_case.details.serie.similar

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetSimilarSeriesUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(serieId: Int) = movaRepository.getSimilarSeries(serieId)
}