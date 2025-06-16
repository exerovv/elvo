package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.order.OrderFullDTO
import com.example.elvo.data.network.models.order.OrderFullInfoDTO
import com.example.elvo.data.network.models.order.OrderShortDTO
import com.example.elvo.data.network.models.order.OrderStatusDTO
import com.example.elvo.data.network.models.order.PaymentDTO
import com.example.elvo.domain.model.order.OrderFull
import com.example.elvo.domain.model.order.OrderFullInfo
import com.example.elvo.domain.model.order.OrderShort
import com.example.elvo.domain.model.order.OrderStatus
import com.example.elvo.domain.model.order.Payment

fun OrderFullDTO.toDomain() = OrderFull(
    this.recipientId,
    this.orderName,
    this.trackNumber,
    this.createdAt,
    this.weight,
    this.totalPrice,
    this.currentStatus,
    this.globalStatus,
    this.paymentStatus,
    this.ruDescription,
    this.chDescription,
    this.link,
    this.itemPrice
)

fun OrderShortDTO.toDomain() = OrderShort(
    this.orderingId,
    this.orderName,
    this.currentStatus,
    this.globalStatus,
    this.paymentStatus
)

fun List<OrderShortDTO>.toDomain() = this.map {
    it.toDomain()
}

fun PaymentDTO.toDomain() = Payment(this.paymentStatus)

fun OrderStatusDTO.toDomain() = OrderStatus(this.name, this.icon, this.createdAt)

fun OrderFullInfoDTO.toDomain() = OrderFullInfo(this.order.toDomain(), this.recipient.toDomain())