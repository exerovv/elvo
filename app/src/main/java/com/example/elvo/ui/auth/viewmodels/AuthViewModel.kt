package com.example.elvo.ui.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.data.network.models.ErrorCode
import com.example.elvo.domain.model.AuthState
import com.example.elvo.domain.usecase.LoginUseCase
import com.example.elvo.domain.usecase.RegisterUseCase
import com.example.elvo.utils.AuthValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _authUIState = MutableSharedFlow<AuthUIState>()
    val authUIState: SharedFlow<AuthUIState>
        get() = _authUIState.asSharedFlow()

    fun login(login: String, password: String){
        viewModelScope.launch {
            loginUseCase(login, password).collect{ result ->
                emitAuthUIState(result)
            }
        }
    }

    fun register(login: String, password: String, confirmationPassword: String){
        viewModelScope.launch {
            registerUseCase(login, password, confirmationPassword).collect{ result ->
                emitAuthUIState(result)
            }
        }
    }

    private suspend fun emitAuthUIState(state: AuthState){
        when(state){
            is AuthState.Loading -> {
                _authUIState.emit(AuthUIState.Loading)
            }
            is AuthState.Success -> {
                _authUIState.emit(AuthUIState.Success)
            }
            is AuthState.ValidationError -> {
                when(state.authValidationResult){
                    AuthValidationResult.InvalidLogin -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_login))
                    }
                    AuthValidationResult.InvalidPassword -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_password))
                    }
                    AuthValidationResult.InvalidConfirmingPassword -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_confirming_password))
                    }
                    else -> {}
                }
            }
            is AuthState.Error -> {
                when(state.errorCode){
                    ErrorCode.INCORRECT_CREDENTIALS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.incorrect_credentials))
                    }
                    ErrorCode.SERVER_ERROR -> {
                        _authUIState.emit(AuthUIState.Error(R.string.server_error))
                    }
                    ErrorCode.SHORT_PASSWORD -> {
                        _authUIState.emit(AuthUIState.Error(R.string.short_password))
                    }
                    ErrorCode.USER_NOT_FOUND -> {
                        _authUIState.emit(AuthUIState.Error(R.string.user_not_found))
                    }
                    ErrorCode.BLANK_CREDENTIALS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.blank_credentials))
                    }
                    ErrorCode.USER_ALREADY_EXISTS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.user_already_exist))
                    }
                    else ->{
                        _authUIState.emit(AuthUIState.Unauthorized)
                    }
                }
            }
        }
    }
}

