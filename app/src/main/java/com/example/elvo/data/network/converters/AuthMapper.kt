package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.auth.TokenDTO
import com.example.elvo.domain.model.auth.Token

fun TokenDTO.toDomain(): Token {
    return Token(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}