package com.example.elvo.domain.repositories

import com.example.elvo.data.network.models.AuthResponse
import com.example.elvo.data.network.models.TokenResponse

interface AuthRepository{
    suspend fun signIn(login: String, password: String) : AuthResponse<TokenResponse>
    suspend fun signUp(login: String, password: String) : AuthResponse<TokenResponse>
}