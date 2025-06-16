package com.example.elvo.domain.model.order

data class OrderFull(
    val recipientId: Int,
    val orderName: String,
    val trackNumber: String,
    val createdAt: String,
    val weight: Double?,
    val totalPrice: Double?,
    val currentStatus: String,
    val globalStatus: String,
    val paymentStatus: String,
    val ruDescription: String,
    val chDescription: String,
    val link: String,
    val itemPrice: Double
)
