package com.example.elvo.data.network.models.recipient

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdatedRecipientDTO(
    @Json(name = "updated_recipient")
    val updatedRecipient: RecipientShortDTO? = null
)
