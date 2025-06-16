package com.example.elvo.utils

import com.example.elvo.domain.model.order.Order

object OrderValidator {
    fun validateAllFields(
        order: Order
    ): Boolean {
        return !(order.recipientId == null || order.orderName.isNullOrBlank() || order.trackNumber.isNullOrBlank() || order.ruDescription.isNullOrBlank() || order.chDescription.isNullOrBlank() || order.itemPrice == null)
    }

    fun validateLink(
        link: String?
    ): Boolean {
        val regex = """^https://dw4\.co/t/A/[a-zA-Z0-9]{9,}$""".toRegex()
        return !(link.isNullOrBlank()) && regex.matches(link)
    }
}