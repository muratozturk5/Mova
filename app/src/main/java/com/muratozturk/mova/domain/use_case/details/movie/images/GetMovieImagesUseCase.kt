package com.muratozturk.mova.domain.use_case.details.movie.images

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetMovieImagesUseCase @Inject constructor(private val repository: MovaRepository) {
    operator fun invoke(movieId: Int) = repository.getMovieImages(movieId)
}