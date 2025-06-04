package com.example.elvo.data.network.models

sealed class AuthResult<out T>{
    data class Success<T>(val data: T?) : AuthResult<T>()
    data class Error(val errorCode: ErrorCode): AuthResult<Unit>()
}
