package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.auth.AuthRequest
import com.example.elvo.data.network.models.auth.RefreshRequest
import com.example.elvo.data.network.models.auth.AuthResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ) : AuthResponseDTO

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): AuthResponseDTO

    @POST("refresh/{id}")
    suspend fun refresh(
        @Path("id") id: Int,
        @Body request: RefreshRequest
    ): AuthResponseDTO

    @POST("logout/{id}")
    suspend fun logout(@Path("id") id: Int)
}