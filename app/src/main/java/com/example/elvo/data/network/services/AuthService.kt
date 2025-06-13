package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.auth.AuthRequest
import com.example.elvo.data.network.models.auth.RefreshRequest
import com.example.elvo.data.network.models.auth.AuthResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ) : AuthResponseDTO

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): AuthResponseDTO

    @POST("refresh")
    suspend fun refresh(
        @Body request: RefreshRequest
    ): AuthResponseDTO
}