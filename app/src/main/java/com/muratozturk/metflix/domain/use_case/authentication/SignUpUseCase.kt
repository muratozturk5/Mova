package com.muratozturk.metflix.domain.use_case.authentication

import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}