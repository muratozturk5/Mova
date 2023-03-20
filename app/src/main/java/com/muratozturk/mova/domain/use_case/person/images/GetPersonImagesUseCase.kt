package com.muratozturk.mova.domain.use_case.person.images

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetPersonImagesUseCase @Inject constructor(private val repository: MovaRepository) {
    operator fun invoke(personId: Int) = repository.getPersonImages(personId)
}