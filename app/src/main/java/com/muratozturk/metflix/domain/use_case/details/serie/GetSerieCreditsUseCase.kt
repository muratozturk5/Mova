package com.muratozturk.metflix.domain.use_case.details.serie

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSerieCreditsUseCase @Inject constructor(val repository: MetflixRepository) {
    operator fun invoke(id: Int) = repository.getSerieCredits(id)
}