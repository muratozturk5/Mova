package com.muratozturk.mova.ui.profile.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.domain.model.LanguageUI
import com.muratozturk.mova.domain.use_case.profile.language.GetCurrentLanguageCodeUseCase
import com.muratozturk.mova.domain.use_case.profile.language.GetLanguagesUseCase
import com.muratozturk.mova.domain.use_case.profile.language.SetCurrentLanguageCodeUseCase
import com.muratozturk.mova.domain.use_case.profile.language.SetCurrentLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val setCurrentLanguageUseCase: SetCurrentLanguageUseCase,
    private val setCurrentLanguageCodeUseCase: SetCurrentLanguageCodeUseCase,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase
) :
    ViewModel() {

    private val _languages = MutableStateFlow<Resource<List<LanguageUI>>>(Resource.Loading)
    val languages get() = _languages.asStateFlow()

    private val _currentLanguageCode = MutableStateFlow<Resource<String>>(Resource.Loading)
    val currentLanguageCode get() = _currentLanguageCode.asStateFlow()

    init {
        getLanguages()
        getCurrentLanguageCode()
    }

    private fun getLanguages() = viewModelScope.launch {
        getLanguagesUseCase().collectLatest {
            _languages.value = it
        }
    }

    private fun getCurrentLanguageCode() = viewModelScope.launch {
        getCurrentLanguageCodeUseCase().collectLatest {
            _currentLanguageCode.value = it
        }
    }


    fun setLanguage(language: String) = viewModelScope.launch {
        setCurrentLanguageUseCase(language)
    }

    fun setLanguageCode(language: String) = viewModelScope.launch {
        setCurrentLanguageCodeUseCase(language)
    }
}