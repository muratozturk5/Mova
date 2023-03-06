package com.muratozturk.metflix.domain.use_case.details.movie.images

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetMovieImagesUseCase @Inject constructor(private val repository: MetflixRepository) {
    operator fun invoke(movieId: Int) = repository.getMovieImages(movieId)
}