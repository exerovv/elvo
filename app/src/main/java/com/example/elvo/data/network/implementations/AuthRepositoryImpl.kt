package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.AuthResponse
import com.example.elvo.data.network.models.TokenResponse
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.domain.repositories.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
) : AuthRepository {
    override suspend fun signIn(login: String, password: String): AuthResponse<TokenResponse> {
        return api.signIn(
            request = AuthRequest(
                username = login,
                password = password
            )
        )
    }


    override suspend fun signUp(login: String, password: String): AuthResponse<TokenResponse> {
        return api.signUp(
            request = AuthRequest(
                username = login,
                password = password
            )
        )
    }
}
