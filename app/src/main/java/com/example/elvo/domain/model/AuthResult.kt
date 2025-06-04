package com.example.elvo.domain.model

sealed class AuthResult {
    data class Success(val data: Token): AuthResult()
    data class Failure(val error: Error): AuthResult()
}