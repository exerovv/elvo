package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipientFullDTO(
    val fullName: String,
    val phone: String,
    val address: String
)
