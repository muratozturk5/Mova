package com.muratozturk.metflix.domain.use_case.details.serie.trailers

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSerieTrailersUseCase @Inject constructor(val repository: MetflixRepository) {
    operator fun invoke(serieId: Int) = repository.getSerieTrailers(serieId)
}