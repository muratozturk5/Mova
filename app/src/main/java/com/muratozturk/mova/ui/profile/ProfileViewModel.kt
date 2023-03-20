package com.muratozturk.mova.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.domain.use_case.profile.GetDarkModeUseCase
import com.muratozturk.mova.domain.use_case.profile.GetUserInfoUseCase
import com.muratozturk.mova.domain.use_case.profile.SetDarkModeUseCase
import com.muratozturk.mova.domain.use_case.profile.language.GetCurrentLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase
) : ViewModel() {


    private val _darkMode = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val darkMode
        get() = _darkMode.asStateFlow()

    private val _userInfo = MutableStateFlow<Resource<FirebaseUser>>(Resource.Loading)
    val userInfo
        get() = _userInfo.asStateFlow()


    private val _currentLanguage = MutableStateFlow<Resource<String>>(Resource.Loading)
    val currentLanguage get() = _currentLanguage.asStateFlow()

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

    fun getCurrentLanguage() = viewModelScope.launch {
        getCurrentLanguageUseCase().collectLatest {
            _currentLanguage.emit(it)
        }
    }
}