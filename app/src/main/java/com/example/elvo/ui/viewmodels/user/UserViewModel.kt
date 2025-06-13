package com.example.elvo.ui.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.user.ProfileResult
import com.example.elvo.domain.usecase.user.FetchUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    fetchUserInfoUseCase: FetchUserInfoUseCase
): ViewModel() {
    private val _userInfoState = MutableStateFlow<UserUIState>(UserUIState.Default)
    val userInfoState: StateFlow<UserUIState>
        get() = _userInfoState.asStateFlow()

    init{
        viewModelScope.launch {
            when(val result = fetchUserInfoUseCase()){
                is ProfileResult.Success -> _userInfoState.value = UserUIState.Success(result.data)
                is ProfileResult.Failure -> {
                    val errorResId = when(result.error.errorCode){
                        ErrorCodes.USER_INFO_LOADING_ERROR -> R.string.user_info_loading_error
                        else -> R.string.unknown_error
                    }
                    _userInfoState.value = UserUIState.Error(errorResId)
                }
            }
        }

    }
}