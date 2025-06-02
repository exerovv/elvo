package com.example.elvo.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse<T>(
    val success: Boolean,
    val errorCode: ErrorCode? = null,
    val data: T? = null
)