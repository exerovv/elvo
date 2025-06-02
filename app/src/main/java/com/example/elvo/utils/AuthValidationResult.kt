package com.example.elvo.utils

sealed class AuthValidationResult {
    data object Success: AuthValidationResult()
    data object InvalidLogin: AuthValidationResult()
    data object InvalidPassword: AuthValidationResult()
    data object InvalidConfirmingPassword: AuthValidationResult()
}