package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.auth.AuthResponseDTO
import com.example.elvo.data.network.models.auth.UserInfoDTO
import com.example.elvo.domain.model.auth.AuthResponse
import com.example.elvo.domain.model.user.UserInfo

fun AuthResponseDTO.toDomain(): AuthResponse {
    return AuthResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        userInfo = this.userInfoDTO?.toDomain()
    )
}

fun UserInfoDTO.toDomain() = UserInfo(this.userId, this.username, this.avatarUrl)