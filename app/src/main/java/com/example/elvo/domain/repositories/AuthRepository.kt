package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.AuthResult

interface AuthRepository{
    suspend fun signIn(login: String, password: String) : AuthResult
    suspend fun signUp(login: String, password: String) : AuthResult
    suspend fun refresh(refreshToken: String): AuthResult
}