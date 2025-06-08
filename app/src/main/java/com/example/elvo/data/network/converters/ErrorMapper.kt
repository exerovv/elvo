package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.ErrorDTO
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error

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
        "REQUIRED_FIELD_EMPTY" -> ErrorCodes.REQUIRED_FIELD_EMPTY
        "NOTHING_CHANGED" -> ErrorCodes.NOTHING_CHANGED
        "RECIPIENT_NOT_FOUND" -> ErrorCodes.RECIPIENT_NOT_FOUND
        "ADDRESS_NOT_FOUND" -> ErrorCodes.ADDRESS_NOT_FOUND
        "INCORRECT_PHONE" -> ErrorCodes.INCORRECT_PHONE
        "INCORRECT_ADDRESS" -> ErrorCodes.INCORRECT_ADDRESS
        "INCORRECT_NAME" -> ErrorCodes.INCORRECT_NAME
        "UNAUTHORIZED" -> ErrorCodes.UNAUTHORIZED
        else -> ErrorCodes.UNKNOWN_ERROR
    }
    return Error(errorCode)
}