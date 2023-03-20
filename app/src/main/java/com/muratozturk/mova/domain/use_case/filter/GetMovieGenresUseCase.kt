package com.muratozturk.mova.domain.use_case.filter

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke() = movaRepository.getMovieGenres()
}