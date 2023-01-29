package com.muratozturk.metflix.domain.use_case.home

import com.muratozturk.metflix.domain.repository.RemoteRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val movieRepository: RemoteRepository) {
    operator fun invoke() = movieRepository.getNowPlayingMovies()
}
