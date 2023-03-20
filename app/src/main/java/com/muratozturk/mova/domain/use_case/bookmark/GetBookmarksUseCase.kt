package com.muratozturk.mova.domain.use_case.bookmark

import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.domain.repository.MovaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val movaRepository: MovaRepository
) {
    operator fun invoke(): Flow<Resource<List<Bookmark>>> = movaRepository.getBookmarks()
}