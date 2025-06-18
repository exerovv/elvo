package com.example.elvo.domain.usecase.auth

import com.example.elvo.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() = authRepository.logout()
}