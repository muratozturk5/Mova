package com.muratozturk.mova.domain.use_case.download

import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class AddDownloadUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    suspend operator fun invoke(download: Download) = movaRepository.addDownload(download)
}