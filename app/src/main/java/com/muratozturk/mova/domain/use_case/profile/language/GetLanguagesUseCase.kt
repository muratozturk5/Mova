package com.muratozturk.mova.domain.use_case.profile.language

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke() = movaRepository.getLanguages()
}