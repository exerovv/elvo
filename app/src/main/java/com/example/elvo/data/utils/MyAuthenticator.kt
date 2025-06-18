package com.example.elvo.data.utils

import com.example.elvo.data.network.models.auth.RefreshResult
import com.example.elvo.domain.model.auth.AuthResult
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class MyAuthenticator @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val authRepository: AuthRepository
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2){
            return null
        }
        return runBlocking(Dispatchers.IO) {
            when(val result = updateToken()){
                is RefreshResult.Success -> {
                    dataStoreRepository.saveTokens(result.accessToken, result.refreshToken)
                    response.request.newBuilder()
                        .addHeader("Authorization", "Bearer ${result.accessToken}")
                        .build()
                }
                else -> null
            }
        }
    }

    private suspend fun updateToken(): RefreshResult{
        val refreshToken = dataStoreRepository.getRefreshToken().first() ?: return RefreshResult.Error
        val result = authRepository.refresh(refreshToken)
        return if (result is AuthResult.Success){
            (RefreshResult.Success(result.data.accessToken, result.data.refreshToken))
        }else{
            RefreshResult.Error
        }
    }

    private fun responseCount(response: Response): Int{
        var count = 1
        var current = response.priorResponse
        while(current != null){
            count++
            current = current.priorResponse
        }
        return count
    }
}