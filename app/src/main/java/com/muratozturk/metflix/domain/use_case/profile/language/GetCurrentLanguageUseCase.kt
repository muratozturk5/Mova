package com.muratozturk.metflix.domain.use_case.profile.language

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetCurrentLanguageUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getCurrentLanguage()
}