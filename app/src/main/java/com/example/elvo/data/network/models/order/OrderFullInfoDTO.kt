package com.example.elvo.data.network.models.order

import com.example.elvo.data.network.models.recipient.RecipientFullDTO
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderFullInfoDTO(
    val order: OrderFullDTO,
    val recipient: RecipientFullDTO
)