package com.muratozturk.metflix.domain.usecase.authentication

import com.muratozturk.metflix.domain.repository.UserRepository
import javax.inject.Inject

class SignUp @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}