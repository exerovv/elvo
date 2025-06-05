package com.example.elvo.domain.model.popular

import com.example.elvo.domain.model.Error

sealed class PopularResult {
    data class Success(val data: List<PopularItem>): PopularResult()
    data class Failure(val error: Error): PopularResult()
}