package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.user.ProfileInfo
import com.example.elvo.domain.model.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveTokens(accessToken : String, refreshToken: String)
    fun getAccessToken() : Flow<String?>
    fun getRefreshToken() : Flow<String?>
    suspend fun clearTokens()
    suspend fun saveUserInfo(userInfo: UserInfo)
    fun getUserInfo() : Flow<ProfileInfo?>
    suspend fun clearUserInfo()
}