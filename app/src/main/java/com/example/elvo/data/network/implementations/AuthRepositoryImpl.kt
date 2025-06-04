package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.ErrorDTO
import com.example.elvo.data.network.models.RefreshRequest
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.domain.model.AuthResult
import com.example.elvo.domain.repositories.AuthRepository
import com.squareup.moshi.Moshi
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
            val errorCode = parseError(errorBody)
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
            val errorCode = parseError(errorBody)
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
            val errorCode = parseError(errorBody)
            AuthResult.Failure(errorCode.toDomain())
        }
    }

    private fun parseError(errorBody: String?): ErrorDTO {
        return try {
            if (errorBody.isNullOrEmpty()) {
                ErrorDTO()
            } else {
                val moshi = Moshi.Builder().build()
                val errorDto = moshi.adapter(ErrorDTO::class.java).fromJson(errorBody) ?: ErrorDTO()
                errorDto
            }
        } catch (e: Exception) {
            ErrorDTO()
        }
    }
}
