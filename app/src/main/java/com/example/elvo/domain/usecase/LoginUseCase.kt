package com.example.elvo.domain.usecase

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.AuthResult
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import com.example.elvo.domain.model.AuthState
import com.example.elvo.domain.model.Error
import com.example.elvo.utils.AuthValidationResult
import com.example.elvo.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(login: String, password: String): Flow<AuthState> {
        val state = flow {
            val validation = Validator.loginValidation(login, password)

            if (validation !is AuthValidationResult.Success) {
                val error = when (validation) {
                    is AuthValidationResult.InvalidLogin -> Error(ErrorCodes.INVALID_LOGIN)
                    is AuthValidationResult.InvalidPassword -> Error(ErrorCodes.INVALID_PASSWORD)
                    is AuthValidationResult.InvalidConfirmingPassword -> Error(ErrorCodes.INVALID_CONFIRMING_PASSWORD)
                    else -> Error(ErrorCodes.UNKNOWN_ERROR)
                }
                emit(AuthState.Failure(error))
                return@flow
            }
            emit(AuthState.Loading)

            val result = authRepository.signIn(login, password)

            if (result is AuthResult.Failure) {
                emit(AuthState.Failure(result.error))
                return@flow
            }

            if (result is AuthResult.Success) {
                try {
                    dataStoreRepository.saveTokens(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken
                    )
                } catch (e: Exception) {
                    emit(AuthState.Failure(Error(ErrorCodes.UNKNOWN_ERROR)))
                    return@flow
                }
            }
            emit(AuthState.Success)
        }
        return state.flowOn(Dispatchers.IO)
    }
}