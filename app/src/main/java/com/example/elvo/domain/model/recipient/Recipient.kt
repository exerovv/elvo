package com.example.elvo.domain.model.recipient

data class Recipient(
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val street: String? = null,
    val house: String? = null,
    val building: String? = null,
    val flat: String? = null,
    val floor: String? = null
)