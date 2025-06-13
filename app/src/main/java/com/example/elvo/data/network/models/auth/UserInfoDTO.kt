package com.example.elvo.data.network.models.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoDTO(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)
