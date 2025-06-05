package com.example.elvo.data.network.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    val username: String,
    val password: String
)