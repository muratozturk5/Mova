package com.muratozturk.mova.domain.use_case.person.series

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetPersonSerieCreditsUseCase @Inject constructor(private val repository: MovaRepository) {
    operator fun invoke(personId: Int) = repository.getPersonSerieCredits(personId)
}