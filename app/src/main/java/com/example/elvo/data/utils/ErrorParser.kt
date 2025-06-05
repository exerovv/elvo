package com.example.elvo.data.utils

import com.example.elvo.data.network.models.ErrorDTO
import com.squareup.moshi.Moshi

object ErrorParser {
    fun parseError(errorBody: String?): ErrorDTO {
        return try {
            if (errorBody.isNullOrEmpty()) {
                ErrorDTO()
            } else {
                val moshi = Moshi.Builder().build()
                val errorDto = moshi.adapter(ErrorDTO::class.java).fromJson(errorBody) ?: ErrorDTO()
                errorDto
            }
        } catch (e: Exception) {
            ErrorDTO()
        }
    }
}