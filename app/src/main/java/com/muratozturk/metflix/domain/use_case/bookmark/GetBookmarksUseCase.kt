package com.muratozturk.metflix.domain.use_case.bookmark

import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.data.model.local.Bookmark
import com.muratozturk.metflix.domain.repository.MetflixRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val metflixRepository: MetflixRepository
) {
    operator fun invoke(): Flow<Resource<List<Bookmark>>> = metflixRepository.getBookmarks()
}