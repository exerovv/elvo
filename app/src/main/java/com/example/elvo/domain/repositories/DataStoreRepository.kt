package com.example.elvo.domain.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveTokens(accessToken : String, refreshToken: String)
    suspend fun clearTokens()
    fun getAccessToken() : Flow<String?>
    fun getRefreshToken() : Flow<String?>
}