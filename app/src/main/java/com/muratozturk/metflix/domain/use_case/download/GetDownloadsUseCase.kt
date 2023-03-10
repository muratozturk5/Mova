package com.muratozturk.metflix.domain.use_case.download

import com.muratozturk.metflix.domain.repository.MetflixRepository
import javax.inject.Inject

class GetDownloadsUseCase @Inject constructor(private val metflixRepository: MetflixRepository) {
    operator fun invoke() = metflixRepository.getDownloads()
}