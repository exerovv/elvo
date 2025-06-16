package com.example.elvo.domain.model.order

import com.example.elvo.domain.model.recipient.RecipientFull

data class OrderFullInfo(
    val order: OrderFull,
    val recipient: RecipientFull
)
