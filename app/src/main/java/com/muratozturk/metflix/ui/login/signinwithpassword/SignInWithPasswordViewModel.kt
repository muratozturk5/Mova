package com.muratozturk.metflix.ui.login.signinwithpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.usecase.authentication.SignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInWithPasswordViewModel @Inject constructor(private val signInUseCase: SignIn) :
    ViewModel() {

    private val _user = MutableStateFlow<Resource<AuthResult>?>(null)
    val user
        get() = _user.asStateFlow()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        signInUseCase(email, password).collect {
            _user.emit(it)
        }
    }
}