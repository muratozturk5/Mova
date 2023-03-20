package com.muratozturk.mova.domain.use_case.profile

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetDarkModeUseCase @Inject constructor(
    private val movaRepository: MovaRepository
) {
    operator fun invoke() = movaRepository.getDarkMode()
}