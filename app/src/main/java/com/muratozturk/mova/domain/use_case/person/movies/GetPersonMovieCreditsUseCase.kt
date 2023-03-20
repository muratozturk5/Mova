package com.muratozturk.mova.domain.use_case.person.movies

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class GetPersonMovieCreditsUseCase @Inject constructor(private val repository: MovaRepository) {
    suspend operator fun invoke(personId: Int) = repository.getPersonMovieCredits(personId)
}
