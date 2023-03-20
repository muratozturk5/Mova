package com.muratozturk.mova.domain.use_case.download

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class RemoveDownloadedUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    suspend operator fun invoke(id: Int) = movaRepository.removeDownloaded(id)
}