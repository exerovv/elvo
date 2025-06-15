package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipientShortDTO(
    @Json(name = "recipient_id")
    val recipientId: Int,
    @Json(name = "full_name")
    val name: String
)