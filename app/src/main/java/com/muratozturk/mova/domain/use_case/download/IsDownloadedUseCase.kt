package com.muratozturk.mova.domain.use_case.download

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class IsDownloadedUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(id: Int) = movaRepository.isDownloaded(id)
}