package com.muratozturk.metflix.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.usecase.profile.SignOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val signOutUseCase: SignOut) : ViewModel() {

    private val _authResult = MutableStateFlow<Resource<Boolean>?>(null)
    val authResult
        get() = _authResult.asStateFlow()

    fun signOut() = viewModelScope.launch {
        signOutUseCase().collect {
            _authResult.emit(it)
        }
    }
}