package com.muratozturk.mova.domain.use_case.bookmark

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class IsBookmarkedUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(id: Int) = movaRepository.isBookmarked(id)
}