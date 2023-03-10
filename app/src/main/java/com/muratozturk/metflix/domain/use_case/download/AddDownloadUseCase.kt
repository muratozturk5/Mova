package com.muratozturk.metflix.domain.use_case.download

import com.muratozturk.metflix.data.model.local.Download
import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class AddDownloadUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    suspend operator fun invoke(download: Download) = metflixRepository.addDownload(download)
}