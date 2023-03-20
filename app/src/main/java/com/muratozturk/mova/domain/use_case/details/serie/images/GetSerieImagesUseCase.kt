package com.muratozturk.mova.domain.use_case.details.serie.images

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetSerieImagesUseCase @Inject constructor(private val repository: MovaRepository) {
    operator fun invoke(serieId: Int) = repository.getSerieImages(serieId)
}