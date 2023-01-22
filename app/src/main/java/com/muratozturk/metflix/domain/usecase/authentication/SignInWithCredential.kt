package com.muratozturk.metflix.domain.usecase.authentication

import com.google.firebase.auth.AuthCredential
import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithCredential @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(credential: AuthCredential) = repository.signInWithCredential(credential)
}