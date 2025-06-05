package com.example.elvo.ui.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.popular.PopularResult
import com.example.elvo.domain.usecase.FetchPopularItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val fetchPopularItemsUseCase: FetchPopularItemsUseCase
): ViewModel() {
    private val _state: MutableStateFlow<PopularUIState> = MutableStateFlow(PopularUIState.Default)
    val state: SharedFlow<PopularUIState>
        get() = _state.asStateFlow()
    init{
        viewModelScope.launch {
            val result = fetchPopularItemsUseCase()
            if (result is PopularResult.Success){
                _state.value = PopularUIState.Success(result.data)
            }
            if (result is PopularResult.Failure){
                val errorResId = when(result.error.errorCode){
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    else -> R.string.unknown_error
                }
                _state.value = PopularUIState.Error(errorResId)
            }
            else{
                _state.value =PopularUIState.Error(R.string.unknown_error)
            }
        }
    }
}