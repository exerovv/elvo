package com.example.elvo.data.network.models.popular

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularItemDTO(
    val title:String,
    val url: String
)
