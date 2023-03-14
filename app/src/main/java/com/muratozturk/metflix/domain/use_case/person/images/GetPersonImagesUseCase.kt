package com.muratozturk.metflix.domain.use_case.person.images

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetPersonImagesUseCase @Inject constructor(private val repository: MetflixRepository) {
    operator fun invoke(personId: Int) = repository.getPersonImages(personId)
}