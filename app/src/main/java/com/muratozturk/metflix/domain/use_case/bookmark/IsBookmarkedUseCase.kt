package com.muratozturk.metflix.domain.use_case.bookmark

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class IsBookmarkedUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(id: Int) = metflixRepository.isBookmarked(id)
}