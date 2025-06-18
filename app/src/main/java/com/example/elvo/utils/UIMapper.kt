package com.example.elvo.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun PaymentStatus.toUiString(): String {
    return when (this) {
        PaymentStatus.NOT_REQUIRED -> "Оплата не требуется"
        PaymentStatus.REQUIRED_NOT_PAID -> "Не оплачено"
        PaymentStatus.REQUIRED_PAID -> "Оплачено"
    }
}



fun formatDateForUi(rawDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale("ru"))
        val date = inputFormat.parse(rawDate)

        if (date != null) outputFormat.format(date) else rawDate
    } catch (e: Exception) {
        rawDate
    }
}