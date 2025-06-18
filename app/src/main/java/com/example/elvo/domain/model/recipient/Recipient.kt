package com.example.elvo.domain.model.recipient

data class Recipient(
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val street: String? = null,
    val house: Int? = null,
    val building: String? = null,
    val flat: Int? = null,
    val floor: Int? = null
)