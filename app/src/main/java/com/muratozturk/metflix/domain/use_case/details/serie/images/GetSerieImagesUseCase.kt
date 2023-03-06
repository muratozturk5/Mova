package com.muratozturk.metflix.domain.use_case.details.serie.images

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetSerieImagesUseCase @Inject constructor(private val repository: MetflixRepository) {
    operator fun invoke(serieId: Int) = repository.getSerieImages(serieId)
}