package com.example.elvo.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDTO(
    @Json(name = "error_code")
    val errorCode: String? = null
)