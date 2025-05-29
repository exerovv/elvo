package com.example.elvo.ui.auth.viewmodels

sealed interface AuthState {
    object Success : AuthState
    data class Error(val error: String) : AuthState
    object Loading : AuthState
    object Default : AuthState
}