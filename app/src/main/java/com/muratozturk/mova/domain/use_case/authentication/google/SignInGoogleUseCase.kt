package com.muratozturk.mova.domain.use_case.authentication.google

import com.muratozturk.mova.domain.repository.AuthRepository
import javax.inject.Inject

class SignInGoogleUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.signInWithGoogle()
}