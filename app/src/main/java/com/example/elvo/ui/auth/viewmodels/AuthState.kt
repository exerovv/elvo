package com.example.elvo.ui.auth.viewmodels

data class AuthState(
    val isLoading: Boolean = false,
    val signUpLogin: String = "",
    val signUpPassword: String = "",
    val signInLogin: String = "",
    val signInPassword: String = ""
)