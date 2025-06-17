package com.example.elvo.data.network.models.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderFullDTO(
    @Json(name = "recipient_id")
    val recipientId: Int,
    @Json(name = "order_name")
    val orderName: String,
    @Json(name = "track_number")
    val trackNumber: String,
    @Json(name = "created_at")
    val createdAt: String,
    val weight: Double?,
    @Json(name = "total_price")
    val totalPrice: Double?,
    @Json(name = "current_status")
    val currentStatus: String,
    @Json(name = "global_status")
    val globalStatus: String,
    @Json(name = "payment_status")
    val paymentStatus: String,
    @Json(name = "ru_description")
    val ruDescription: String,
    @Json(name = "ch_description")
    val chDescription: String,
    val link: String,
    @Json(name = "item_price")
    val itemPrice: Double
)
