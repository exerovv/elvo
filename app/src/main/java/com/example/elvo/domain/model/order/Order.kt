package com.example.elvo.domain.model.order

data class Order(
    val recipientId: Int?,
    val orderName: String?,
    val trackNumber: String?,
    val ruDescription: String?,
    val chDescription: String?,
    val link: String?,
    val itemPrice: Double?
)