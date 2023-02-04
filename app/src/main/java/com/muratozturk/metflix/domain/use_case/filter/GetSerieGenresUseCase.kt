package com.muratozturk.metflix.domain.use_case.filter

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSerieGenresUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getSerieGenres()
}