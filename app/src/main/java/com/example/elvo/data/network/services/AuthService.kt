package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.RefreshRequest
import com.example.elvo.data.network.models.TokenDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ) : TokenDTO

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenDTO

    @POST("refresh")
    suspend fun refresh(
        @Body request: RefreshRequest
    ): TokenDTO
}