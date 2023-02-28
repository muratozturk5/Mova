package com.muratozturk.metflix.domain.use_case.details.serie

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSerieDetailsUseCase @Inject constructor(val repository: MetflixRepository) {
    operator fun invoke(id: Int) = repository.getSerieDetails(id)
}