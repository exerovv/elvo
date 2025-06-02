package com.example.elvo.domain.model

import com.example.elvo.data.network.models.ErrorCode
import com.example.elvo.utils.AuthValidationResult

sealed class AuthState {
    data object Success : AuthState()
    data class Error(val errorCode: ErrorCode?): AuthState()
    data class ValidationError(val authValidationResult: AuthValidationResult): AuthState()
    data object Loading: AuthState()
}