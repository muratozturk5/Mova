package com.muratozturk.mova.domain.use_case.download

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetDownloadsUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke() = movaRepository.getDownloads()
}