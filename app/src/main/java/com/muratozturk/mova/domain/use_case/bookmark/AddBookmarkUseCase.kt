package com.muratozturk.mova.domain.use_case.bookmark

import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    suspend operator fun invoke(bookmark: Bookmark) {
        movaRepository.addBookmark(bookmark)
    }
}