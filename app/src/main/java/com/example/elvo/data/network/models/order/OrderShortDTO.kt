package com.example.elvo.data.network.models.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderShortDTO(
    @Json(name = "ordering_id")
    val orderingId: Int,
    @Json(name = "order_name")
    val orderName: String,
    @Json(name = "current_status")
    val currentStatus: String,
    @Json(name = "global_status")
    val globalStatus: String,
    @Json(name = "payment_status")
    val paymentStatus: String
)
