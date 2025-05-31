package com.example.elvo.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)