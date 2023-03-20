package com.muratozturk.mova.domain.use_case.home

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetNowPlayingSeriesUseCase @Inject constructor(private val movieRepository: MovaRepository) {
    operator fun invoke() = movieRepository.getNowPlayingSeries()
}
