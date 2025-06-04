package com.example.elvo.domain.model

import com.example.elvo.domain.enums.ErrorCodes

data class Error(
    val errorCode: ErrorCodes
)