package com.muratozturk.metflix.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.use_case.profile.GetDarkModeUseCase
import com.muratozturk.metflix.domain.use_case.profile.GetUserInfoUseCase
import com.muratozturk.metflix.domain.use_case.profile.SetDarkModeUseCase
import com.muratozturk.metflix.domain.use_case.profile.SignOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOut,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase
) : ViewModel() {

    private val _authResult = MutableSharedFlow<Resource<Boolean>>()
    val authResult
        get() = _authResult.asSharedFlow()

    private val _darkMode = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val darkMode
        get() = _darkMode.asStateFlow()

    private val _userInfo = MutableStateFlow<Resource<FirebaseUser>>(Resource.Loading)
    val userInfo
        get() = _userInfo.asStateFlow()

    init {
        getUserInfo()

    }

    fun setDarkMode(isDarkMode: Boolean) = viewModelScope.launch {
        setDarkModeUseCase(isDarkMode)
    }

    fun getDarkMode() = viewModelScope.launch {
        getDarkModeUseCase().collectLatest {
            _darkMode.emit(it)
        }
    }

    private fun getUserInfo() = viewModelScope.launch {
        getUserInfoUseCase().collectLatest {
            _userInfo.emit(it)
        }
    }

    fun signOut() = viewModelScope.launch {
        signOutUseCase().collectLatest {
            _authResult.emit(it)
        }
    }
}