package com.muratozturk.metflix.ui.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.usecase.authentication.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUp) : ViewModel() {

    private val _user = MutableStateFlow<Resource<AuthResult>?>(null)
    val user
        get() = _user.asStateFlow()

    fun signUp(email: String, password: String) = viewModelScope.launch {
        signUpUseCase(email, password).collect {
            _user.emit(it)
        }
    }
}