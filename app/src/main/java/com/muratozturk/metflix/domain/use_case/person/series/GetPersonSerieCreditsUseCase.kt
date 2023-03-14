package com.muratozturk.metflix.domain.use_case.person.series

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetPersonSerieCreditsUseCase @Inject constructor(private val repository: MetflixRepository) {
    operator fun invoke(personId: Int) = repository.getPersonSerieCredits(personId)
}