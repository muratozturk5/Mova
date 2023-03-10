package com.muratozturk.metflix.domain.use_case.download

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class IsDownloadedUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke(id: Int) = metflixRepository.isDownloaded(id)
}