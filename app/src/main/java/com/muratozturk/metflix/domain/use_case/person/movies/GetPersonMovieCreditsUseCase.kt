package com.muratozturk.metflix.domain.use_case.person.movies

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetPersonMovieCreditsUseCase @Inject constructor(private val repository: MetflixRepository) {
    suspend operator fun invoke(personId: Int) = repository.getPersonMovieCredits(personId)
}
