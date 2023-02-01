package com.muratozturk.metflix.domain.use_case.home

import com.muratozturk.metflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingSeriesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke() = movieRepository.getNowPlayingSeries()
}
