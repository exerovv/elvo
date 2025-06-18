package com.example.elvo.data.network.implementations

import android.util.Log
import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.auth.AuthRequest
import com.example.elvo.data.network.models.auth.RefreshRequest
import com.example.elvo.data.network.services.AuthService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.auth.AuthResult
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
    private val dataStoreRepository: DataStoreRepository
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

    override suspend fun refresh(): AuthResult {
        return try {
            val id = dataStoreRepository.getUserId().first()
            if(id != null){
                val refreshToken = dataStoreRepository.getRefreshToken().first()
                if (refreshToken != null) {
                    val result = id.let {
                        api.refresh(
                            it,
                            RefreshRequest(
                                refreshToken = refreshToken
                            )
                        )
                    }
                    dataStoreRepository.saveTokens(
                        accessToken = result.accessToken,
                        refreshToken = result.refreshToken
                    )
                }
                AuthResult.Success()
            }else{
                AuthResult.Failure(Error(ErrorCodes.UNKNOWN_ERROR))
            }
        }catch (e: Exception) {
            AuthResult.Failure(Error(ErrorCodes.UNKNOWN_ERROR))
        }
    }

    override suspend fun logout(): AuthResult {
        return try{
            val id = dataStoreRepository.getUserId().first()
            Log.d("Logout", "Start")
            id?.let{ api.logout(it) }
            Log.d("Logout", "Before")
            dataStoreRepository.clearTokens()
            dataStoreRepository.clearUserInfo()
            Log.d("Logout", "After")
            AuthResult.Success()
        }catch (e: Exception) {
            AuthResult.Failure(Error(ErrorCodes.UNKNOWN_ERROR))
        }
    }
}
