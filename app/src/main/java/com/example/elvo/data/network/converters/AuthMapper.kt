package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.ErrorDTO
import com.example.elvo.data.network.models.auth.TokenDTO
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.auth.Token

fun TokenDTO.toDomain(): Token {
    return Token(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun ErrorDTO.toDomain(): Error {
    val errorCode = when (this.errorCode) {
        "INCORRECT_CREDENTIALS" -> ErrorCodes.INCORRECT_CREDENTIALS
        "USER_NOT_FOUND" -> ErrorCodes.USER_NOT_FOUND
        "BLANK_CREDENTIALS" -> ErrorCodes.BLANK_CREDENTIALS
        "SHORT_PASSWORD" -> ErrorCodes.SHORT_PASSWORD
        "USER_ALREADY_EXISTS" -> ErrorCodes.USER_ALREADY_EXISTS
        "SERVER_ERROR" -> ErrorCodes.SERVER_ERROR
        "SESSION_EXPIRED" -> ErrorCodes.SESSION_EXPIRED
        "CHECK_CREDENTIALS" -> ErrorCodes.CHECK_CREDENTIALS
        else -> ErrorCodes.UNKNOWN_ERROR
    }
    return Error(errorCode)
}