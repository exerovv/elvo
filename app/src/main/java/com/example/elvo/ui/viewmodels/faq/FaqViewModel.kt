package com.example.elvo.ui.viewmodels.faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.faq.FaqResult
import com.example.elvo.domain.usecase.faq.FetchFaqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(
    private val fetchFaqUseCase: FetchFaqUseCase
): ViewModel() {
    private val _state: MutableStateFlow<FaqUIState> = MutableStateFlow(FaqUIState.Default)
    val state: StateFlow<FaqUIState>
        get() = _state.asStateFlow()
    init {
        viewModelScope.launch {
            when(val result = fetchFaqUseCase()){
                is FaqResult.Success -> _state.value = FaqUIState.Success(result.data)
                is FaqResult.Failure -> {
                    val errorResId = when(result.error.errorCode){
                        ErrorCodes.SERVER_ERROR -> R.string.server_error
                        else -> R.string.unknown_error
                    }
                    _state.value = FaqUIState.Error(errorResId)
                }
            }
        }
    }
}