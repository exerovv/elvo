package com.example.elvo.data.network.models.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderStatusDTO(
    val name: String,
    val icon: String,
    @Json(name = "created_at")
    val createdAt: String
)