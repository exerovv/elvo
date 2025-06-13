package com.example.elvo.domain.model.auth

import com.example.elvo.domain.model.user.UserInfo

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userInfo: UserInfo?
)