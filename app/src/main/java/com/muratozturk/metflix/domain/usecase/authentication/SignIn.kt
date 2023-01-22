package com.muratozturk.metflix.domain.usecase.authentication

import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignIn @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}