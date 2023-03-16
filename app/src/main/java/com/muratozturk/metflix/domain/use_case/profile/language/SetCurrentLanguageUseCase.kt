package com.muratozturk.metflix.domain.use_case.profile.language

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class SetCurrentLanguageUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(language: String) = metflixRepository.setCurrentLanguage(language)
}