package com.example.elvo.data.network.models.order

import com.example.elvo.data.network.models.recipient.RecipientFullDTO

data class OrderFullInfoDTO(
    val order: OrderFullDTO,
    val recipient: RecipientFullDTO
)