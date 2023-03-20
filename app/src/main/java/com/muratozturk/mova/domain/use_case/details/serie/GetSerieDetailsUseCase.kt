package com.muratozturk.mova.domain.use_case.details.serie

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetSerieDetailsUseCase @Inject constructor(val repository: MovaRepository) {
    operator fun invoke(id: Int) = repository.getSerieDetails(id)
}