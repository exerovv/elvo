package com.example.elvo.domain.model.recipient

data class RecipientFull(
    val fullName: String,
    val phone: String,
    val address: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val city: String,
    val street: String,
    val house: Int,
    val building: String? = null,
    val flat: Int,
    val floor: Int,
)