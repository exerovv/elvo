package com.example.elvo.data.network.models

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null): AuthResult<T>(data)
    class Unauthorized<T>(): AuthResult<T>()
    class UnknownError<T>(data: T? = null): AuthResult<T>(data)
}