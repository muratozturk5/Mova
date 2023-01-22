package com.muratozturk.metflix.domain.usecase.authentication.github

import android.app.Activity
import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignInGithub @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(activity: Activity) = repository.signInWithGithub(activity)
}