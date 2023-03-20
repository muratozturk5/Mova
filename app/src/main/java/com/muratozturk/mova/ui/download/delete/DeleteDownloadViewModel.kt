package com.muratozturk.mova.ui.download.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.domain.use_case.download.RemoveDownloadedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteDownloadViewModel @Inject constructor(private val removeDownloadedUseCase: RemoveDownloadedUseCase) :
    ViewModel() {

    fun removeDownloaded(id: Int) = viewModelScope.launch {
        removeDownloadedUseCase(id)
    }
}