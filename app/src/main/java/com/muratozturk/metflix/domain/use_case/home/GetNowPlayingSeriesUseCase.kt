package com.muratozturk.metflix.domain.use_case.home

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetNowPlayingSeriesUseCase @Inject constructor(private val movieRepository: MetflixRepository) {
    operator fun invoke() = movieRepository.getNowPlayingSeries()
}
