package com.muratozturk.metflix.domain.use_case.download

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class RemoveDownloadedUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    suspend operator fun invoke(id: Int) = metflixRepository.removeDownloaded(id)
}