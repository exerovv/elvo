package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipientFullDTO(
    val name: String,
    val surname: String,
    val patronymic: String,
    val fullName: String,
    val phone: String,
    val city: String,
    val street: String,
    val house: Int,
    val building: String,
    val flat: Int,
    val floor: Int,
    val address: String
)
