package com.example.elvo.domain.model.auth

import com.example.elvo.domain.model.Error

sealed class AuthResult {
    data class Success(val data: AuthResponse): AuthResult()
    data class Failure(val error: Error): AuthResult()
}