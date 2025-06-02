package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.AuthResponse
import com.example.elvo.data.network.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ) : AuthResponse<TokenResponse>

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): AuthResponse<TokenResponse>

}