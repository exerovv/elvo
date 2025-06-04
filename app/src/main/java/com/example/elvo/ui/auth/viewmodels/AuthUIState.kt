package com.example.elvo.ui.auth.viewmodels

sealed class AuthUIState {
    data object Success: AuthUIState()
    data class Error(val errorResId: Int): AuthUIState()
    data object Unauthorized: AuthUIState()
    data object UnknownError: AuthUIState()
    data object Loading: AuthUIState()
}