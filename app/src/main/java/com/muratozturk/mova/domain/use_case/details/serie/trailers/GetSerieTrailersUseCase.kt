package com.muratozturk.mova.domain.use_case.details.serie.trailers

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetSerieTrailersUseCase @Inject constructor(val repository: MovaRepository) {
    operator fun invoke(serieId: Int) = repository.getSerieTrailers(serieId)
}