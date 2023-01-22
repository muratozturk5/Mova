package com.muratozturk.metflix.domain.usecase.authentication.google

import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignInGoogle @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.signInWithGoogle()
}