package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipientRequest(
    @Json(name = "name")
    val name: String,
    @Json(name = "surname")
    val surname: String,
    @Json(name = "patronymic")
    val patronymic: String?,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "street")
    val street: String,
    @Json(name = "house")
    val house: Int,
    @Json(name = "building")
    val building: String?,
    @Json(name = "flat")
    val flat: Int,
    @Json(name = "floor")
    val floor: Int
)