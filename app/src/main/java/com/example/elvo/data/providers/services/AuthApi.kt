package com.example.elvo.data.providers.services

import com.example.elvo.data.providers.models.AuthRequest
import com.example.elvo.data.providers.models.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("signin")
    suspend fun signIn(@Body request: AuthRequest): Response<AuthResponse>
}