package com.muratozturk.metflix.domain.use_case.bookmark

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    suspend operator fun invoke(id: Int) {
        metflixRepository.removeBookmark(id)
    }
}