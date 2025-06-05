package com.example.elvo.data.network.models.popular

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularItemsDTO(
    val title:String,
    val url: String
)
