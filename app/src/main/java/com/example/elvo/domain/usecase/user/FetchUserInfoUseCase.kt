package com.example.elvo.domain.usecase.user

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.user.ProfileResult
import com.example.elvo.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): ProfileResult{
        try{
            val result = dataStoreRepository.getUserInfo().first()
            if (result != null){
                if (result.username != null && result.avatarUrl != null){
                    return ProfileResult.Success(result)
                }
            }
            return ProfileResult.Failure(Error(ErrorCodes.USER_INFO_LOADING_ERROR))
        }catch (_: Exception){
            return ProfileResult.Failure(Error(ErrorCodes.UNKNOWN_ERROR))
        }
    }
}