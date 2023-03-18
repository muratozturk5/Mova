package com.muratozturk.metflix.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.model.PersonDetailsUI
import com.muratozturk.metflix.domain.use_case.person.GetPersonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase
) :
    ViewModel() {

    private val _personDetails = MutableSharedFlow<Resource<PersonDetailsUI>>()
    val personDetails
        get() = _personDetails.asSharedFlow()


    fun getPersonDetails(id: Int) = viewModelScope.launch {
        getPersonDetailsUseCase(id).collectLatest {
            _personDetails.emit(it)
        }
    }
}