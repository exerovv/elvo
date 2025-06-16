package com.example.elvo.data.network.models.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentDTO(
    val paymentStatus: String
)