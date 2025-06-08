package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipientShortDTO(
    val recipientId: Int,
    val name: String
)