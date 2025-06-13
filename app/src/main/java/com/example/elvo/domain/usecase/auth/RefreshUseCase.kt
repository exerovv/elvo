package com.example.elvo.domain.usecase.auth

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.auth.AuthResult
import com.example.elvo.domain.model.auth.AuthState
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke(): Flow<AuthState> = flow{
        try{
            val refreshToken = dataStoreRepository.getRefreshToken().first()
            if (refreshToken != null) {
                val result = authRepository.refresh(refreshToken = refreshToken)
                if (result is AuthResult.Failure){
                    emit(AuthState.Failure(result.error))
                    return@flow
                }
                if(result is AuthResult.Success){
                    try {
                        dataStoreRepository.saveTokens(
                            accessToken = result.data.accessToken,
                            refreshToken = result.data.refreshToken
                        )
                    } catch (e: Exception) {
                        dataStoreRepository.clearTokens()
                        dataStoreRepository.clearUserInfo()
                        emit(AuthState.Failure(Error(ErrorCodes.UNKNOWN_ERROR)))
                        return@flow
                    }
                }
                emit(AuthState.Success)
            }
        }catch (e: Exception){
            emit(AuthState.Failure(Error(ErrorCodes.UNKNOWN_ERROR)))
            return@flow
        }
    }.flowOn(Dispatchers.IO)
}