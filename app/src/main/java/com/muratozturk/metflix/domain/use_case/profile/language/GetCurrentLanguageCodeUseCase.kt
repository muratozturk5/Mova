package com.muratozturk.metflix.domain.use_case.profile.language

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetCurrentLanguageCodeUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getCurrentLanguageCode()
}