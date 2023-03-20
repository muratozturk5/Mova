package com.muratozturk.mova.ui.authentication.signinwithpassword

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
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.domain.use_case.authentication.SignInUseCase
import com.muratozturk.mova.domain.use_case.authentication.SignInWithCredentialUseCase
import com.muratozturk.mova.domain.use_case.authentication.github.SignInGithubUseCase
import com.muratozturk.mova.domain.use_case.authentication.google.SignInGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInWithPasswordViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val signInGoogleUseCase: SignInGoogleUseCase,
    private val signInGithubUseCase: SignInGithubUseCase
) :
    ViewModel() {

    private val _user = MutableSharedFlow<Resource<AuthResult>>()
    val user
        get() = _user.asSharedFlow()

    private val _googleIntent = MutableSharedFlow<Resource<Intent>>()
    val googleIntent
        get() = _googleIntent.asSharedFlow()

    private val _credentialSignInResult = MutableSharedFlow<Resource<AuthResult>>()
    val credentialSignInResult
        get() = _credentialSignInResult.asSharedFlow()

    private val _facebookSignIn = MutableSharedFlow<Resource<AuthCredential>>()
    val facebookSignIn
        get() = _facebookSignIn.asSharedFlow()


    fun signIn(email: String, password: String) = viewModelScope.launch {
        signInUseCase(email, password).collectLatest {
            _user.emit(it)
        }
    }

    fun signInWithCredential(credential: AuthCredential) = viewModelScope.launch {
        signInWithCredentialUseCase(credential).collectLatest {
            _credentialSignInResult.emit(it)
        }
    }

    fun signInGoogle() = viewModelScope.launch {
        signInGoogleUseCase().collectLatest {
            _googleIntent.emit(it)
        }
    }

    fun signInGithub(activity: Activity) = viewModelScope.launch {
        signInGithubUseCase(activity).collectLatest {
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