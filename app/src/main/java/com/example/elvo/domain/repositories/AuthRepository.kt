package com.example.elvo.domain.repositories

import com.example.elvo.data.network.models.AuthResult

interface AuthRepository{
    suspend fun signIn(login: String, password: String) : AuthResult<Unit>
    suspend fun signUp(login: String, password: String) : AuthResult<Unit>
}