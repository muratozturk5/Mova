package com.muratozturk.metflix.domain.use_case.filter

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getMovieGenres()
}