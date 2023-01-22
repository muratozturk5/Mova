package com.muratozturk.metflix.domain.usecase.authentication

import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignUp @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}