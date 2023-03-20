package com.muratozturk.mova.domain.use_case.person

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(id: Int) = movaRepository.getPersonDetails(id)
}