package com.muratozturk.metflix.domain.use_case.profile.language

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getLanguages()
}