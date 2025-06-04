package com.example.elvo.data.network.implementations

import coil3.network.HttpException
import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.AuthResponse
import com.example.elvo.data.network.models.AuthResult
import com.example.elvo.data.network.models.ErrorCode
import com.example.elvo.data.network.models.TokenResponse
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.domain.repositories.AuthRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
) : AuthRepository {
    override suspend fun signIn(login: String, password: String): AuthResult<Any> {
        return try{
            val response = api.signIn(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            AuthResult.Success<TokenResponse>(response.data)
        }catch(e: HttpException){
            val errorBody = e.response.body.toString()
            AuthResult.Error(parseError(errorBody))
        }
    }


    override suspend fun signUp(login: String, password: String): AuthResult<Any> {
        return try{
            val response = api.signUp(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            AuthResult.Success(response.data)
        }catch(e: HttpException){
            val errorBody = e.response.body.toString()
            AuthResult.Error(parseError(errorBody))
        }
    }

    private fun parseError(errorBody: String): ErrorCode{
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(AuthResponse::class.java, Unit::class.java)
        val adapter = moshi.adapter<AuthResponse<Unit>>(type)
        return adapter.fromJson(errorBody)?.errorCode ?: ErrorCode.SERVER_ERROR
    }
}
