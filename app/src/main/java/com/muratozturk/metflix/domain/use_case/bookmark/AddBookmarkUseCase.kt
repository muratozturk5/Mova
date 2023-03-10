package com.muratozturk.metflix.domain.use_case.bookmark

import com.muratozturk.metflix.data.model.local.Bookmark
import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    suspend operator fun invoke(bookmark: Bookmark) {
        metflixRepository.addBookmark(bookmark)
    }
}