package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.auth.AuthRequest
import com.example.elvo.data.network.models.auth.RefreshRequest
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.domain.model.auth.AuthResult
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.data.utils.ErrorParser
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
) : AuthRepository {
    override suspend fun signIn(login: String, password: String): AuthResult {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            AuthResult.Success(response.toDomain())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorCode = ErrorParser.parseError(errorBody)
            AuthResult.Failure(errorCode.toDomain())
        }
    }


    override suspend fun signUp(login: String, password: String): AuthResult {
        return try {
            val response = api.signUp(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            AuthResult.Success(response.toDomain())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorCode = ErrorParser.parseError(errorBody)
            AuthResult.Failure(errorCode.toDomain())
        }
    }

    override suspend fun refresh(refreshToken: String): AuthResult {
        return try {
            val response = api.refresh(
                RefreshRequest(
                    refreshToken = refreshToken
                )
            )
            AuthResult.Success(response.toDomain())
        }catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorCode = ErrorParser.parseError(errorBody)
            AuthResult.Failure(errorCode.toDomain())
        }
    }
}
