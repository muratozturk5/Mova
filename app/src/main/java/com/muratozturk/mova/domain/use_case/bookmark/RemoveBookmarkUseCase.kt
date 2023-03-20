package com.muratozturk.mova.domain.use_case.bookmark

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    suspend operator fun invoke(id: Int) {
        movaRepository.removeBookmark(id)
    }
}