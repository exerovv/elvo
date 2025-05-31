package com.example.elvo.data.network.implementations

import coil3.network.HttpException
import com.example.elvo.data.network.models.AuthRequest
import com.example.elvo.data.network.models.AuthResult
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStorePreferencesRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
    private val dataStorePreferencesRepositoryImpl: DataStorePreferencesRepository
) : AuthRepository {
    override suspend fun signIn(login: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            dataStorePreferencesRepositoryImpl.saveTokens(response.accessToken, response.refreshToken)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }

    }


    override suspend fun signUp(login: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signUp(
                request = AuthRequest(
                    username = login,
                    password = password
                )
            )
            dataStorePreferencesRepositoryImpl.saveTokens(response.accessToken, response.refreshToken)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
