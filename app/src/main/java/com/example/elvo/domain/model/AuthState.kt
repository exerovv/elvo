package com.example.elvo.domain.model


sealed class AuthState {
    data object Success : AuthState()
    data class Failure(val error: Error): AuthState()
    data object Loading: AuthState()
}