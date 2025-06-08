package com.example.elvo.domain.model.recipient

data class Recipient(
    val name: String?,
    val surname: String?,
    val patronymic: String?,
    val phone: String?,
    val city: String?,
    val street: String?,
    val house: String?,
    val building: String?,
    val flat: String?,
    val floor: String?
)