package com.example.elvo.ui.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.auth.AuthState
import com.example.elvo.domain.usecase.auth.LoginUseCase
import com.example.elvo.domain.usecase.auth.RefreshUseCase
import com.example.elvo.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {
    private val _authUIState = MutableSharedFlow<AuthUIState>()
    val authUIState: SharedFlow<AuthUIState>
        get() = _authUIState.asSharedFlow()

    fun login(login: String, password: String) {
        viewModelScope.launch {
            loginUseCase(login, password).collect { result ->
                emitAuthUIState(result)
            }
        }
    }

    fun register(login: String, password: String, confirmationPassword: String) {
        viewModelScope.launch {
            registerUseCase(login, password, confirmationPassword).collect { result ->
                emitAuthUIState(result)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshUseCase().collect { result ->
                emitAuthUIState(result)
            }
        }
    }

    private suspend fun emitAuthUIState(state: AuthState) {
        when (state) {
            is AuthState.Loading -> {
                _authUIState.emit(AuthUIState.Loading)
            }

            is AuthState.Success -> {
                _authUIState.emit(AuthUIState.Success)
            }

            is AuthState.Failure -> {
                when (state.error.errorCode) {
                    ErrorCodes.INCORRECT_CREDENTIALS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.incorrect_credentials))
                    }

                    ErrorCodes.SERVER_ERROR -> {
                        _authUIState.emit(AuthUIState.Error(R.string.server_error))
                    }

                    ErrorCodes.SHORT_PASSWORD -> {
                        _authUIState.emit(AuthUIState.Error(R.string.short_password))
                    }

                    ErrorCodes.USER_NOT_FOUND -> {
                        _authUIState.emit(AuthUIState.Error(R.string.user_not_found))
                    }

                    ErrorCodes.BLANK_CREDENTIALS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.blank_credentials))
                    }

                    ErrorCodes.USER_ALREADY_EXISTS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.user_already_exist))
                    }

                    ErrorCodes.SESSION_EXPIRED -> {
                        _authUIState.emit(AuthUIState.Unauthorized)
                    }

                    ErrorCodes.INVALID_LOGIN -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_login))
                    }

                    ErrorCodes.INVALID_PASSWORD -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_password))
                    }

                    ErrorCodes.INVALID_CONFIRMING_PASSWORD -> {
                        _authUIState.emit(AuthUIState.Error(R.string.invalid_confirming_password))
                    }

                    ErrorCodes.CHECK_CREDENTIALS -> {
                        _authUIState.emit(AuthUIState.Error(R.string.check_credentials))
                    }

                    else -> {
                        _authUIState.emit(AuthUIState.UnknownError)
                    }
                }
            }
        }
    }
}

