package com.example.elvo.data.network.models.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderAddRequest(
    val recipientId: Int,
    val orderName: String,
    val trackNumber: String,
    val ruDescription: String,
    val chDescription: String,
    val link: String,
    val itemPrice: Double
)
