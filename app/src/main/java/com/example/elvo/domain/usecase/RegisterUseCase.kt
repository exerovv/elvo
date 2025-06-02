package com.example.elvo.domain.usecase

import com.example.elvo.data.network.models.AuthResponse
import com.example.elvo.data.network.models.TokenResponse
import com.example.elvo.domain.model.AuthState
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import com.example.elvo.utils.AuthValidationResult
import com.example.elvo.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
){
    operator fun invoke(login: String, password: String, confirmationPassword: String): Flow<AuthState> {
        val state = flow {
            val validation = Validator.registerValidation(login, password, confirmationPassword)

            if (validation !is AuthValidationResult.Success) {
                emit(AuthState.ValidationError(authValidationResult = validation))
                return@flow
            }
            emit(AuthState.Loading)

            val result: AuthResponse<TokenResponse> = authRepository.signUp(login, password)

            if (!result.success) {
                emit(AuthState.Error(result.errorCode))
                return@flow
            }

            if (result.data != null) {
                dataStoreRepository.saveTokens(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken
                )
            }
            emit(AuthState.Success)
        }
        return state.flowOn(Dispatchers.IO)
    }
}