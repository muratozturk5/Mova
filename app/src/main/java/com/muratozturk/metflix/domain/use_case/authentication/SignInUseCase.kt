package com.muratozturk.metflix.domain.use_case.authentication

import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}