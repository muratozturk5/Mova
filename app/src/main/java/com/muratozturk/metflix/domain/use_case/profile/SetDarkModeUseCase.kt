package com.muratozturk.metflix.domain.use_case.profile

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(
    private val metflixRepository: MetflixRepository
) {
    operator fun invoke(isDarkMode: Boolean) = metflixRepository.setDarkMode(isDarkMode)
}
