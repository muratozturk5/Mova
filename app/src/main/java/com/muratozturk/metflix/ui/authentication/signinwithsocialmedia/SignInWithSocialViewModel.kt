package com.muratozturk.metflix.ui.authentication.signinwithsocialmedia

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.usecase.authentication.SignInWithCredential
import com.muratozturk.metflix.domain.usecase.authentication.github.SignInGithub
import com.muratozturk.metflix.domain.usecase.authentication.google.SignInGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInWithSocialViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredential,
    private val signInGoogleUseCase: SignInGoogle,
    private val signInGithubUseCase: SignInGithub
) : ViewModel() {

    private val _googleIntent = MutableStateFlow<Resource<Intent>?>(null)
    val googleIntent
        get() = _googleIntent.asStateFlow()

    private val _credentialSignInResult = MutableStateFlow<Resource<AuthResult>?>(null)
    val credentialSignInResult
        get() = _credentialSignInResult.asStateFlow()

    private val _facebookSignIn = MutableStateFlow<Resource<AuthCredential>?>(null)
    val facebookSignIn
        get() = _facebookSignIn.asStateFlow()

    fun signInWithCredential(credential: AuthCredential) = viewModelScope.launch {
        signInWithCredentialUseCase(credential).collect {
            _credentialSignInResult.emit(it)
        }
    }

    fun signInGoogle() = viewModelScope.launch {
        signInGoogleUseCase().collect {
            _googleIntent.emit(it)
        }
    }

    fun signInGithub(activity: Activity) = viewModelScope.launch {
        signInGithubUseCase(activity).collect {
            _credentialSignInResult.emit(it)
        }
    }

    val loginManager: LoginManager = LoginManager.getInstance()
    val mCallbackManager = CallbackManager.Factory.create()
    private val mFacebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult) {

            val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
            Log.e("credential", credential.toString())
            viewModelScope.launch {
                _facebookSignIn.emit(Resource.Success(credential))
            }
        }

        override fun onCancel() {

        }

        override fun onError(error: FacebookException) {
            viewModelScope.launch {
                _facebookSignIn.emit(Resource.Error(error))
            }
        }
    }

    init {
        loginManager.registerCallback(mCallbackManager, mFacebookCallback)
    }

}