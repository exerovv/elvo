package com.example.elvo.data.network.models.auth

import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName

@JsonClass(generateAdapter = true)
data class RefreshRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)