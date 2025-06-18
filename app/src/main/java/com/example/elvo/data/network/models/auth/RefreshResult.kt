package com.example.elvo.data.network.models.auth

sealed class RefreshResult {
    data class Success(val accessToken: String, val refreshToken: String): RefreshResult()
    data object Error: RefreshResult()
}