package com.example.elvo.data.network.models.faq

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FaqDTO (
    val question: String,
    val answer: String
)