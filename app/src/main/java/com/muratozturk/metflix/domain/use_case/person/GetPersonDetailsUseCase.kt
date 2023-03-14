package com.muratozturk.metflix.domain.use_case.person

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(id: Int) = metflixRepository.getPersonDetails(id)
}