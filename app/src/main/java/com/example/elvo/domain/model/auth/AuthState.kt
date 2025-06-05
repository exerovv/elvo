package com.example.elvo.domain.model.auth

import com.example.elvo.domain.model.Error


sealed class AuthState {
    data object Success : AuthState()
    data class Failure(val error: Error): AuthState()
    data object Loading: AuthState()
}