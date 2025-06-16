package com.example.elvo.domain.model.order

data class OrderShort(
    val orderingId: Int,
    val orderName: String,
    val currentStatus: String,
    val globalStatus: String,
    val paymentStatus: String
)
