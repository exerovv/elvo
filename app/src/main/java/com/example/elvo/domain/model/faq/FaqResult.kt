package com.example.elvo.domain.model.faq

import com.example.elvo.domain.model.Error

sealed class FaqResult {
    data class Success(val data: List<Faq>): FaqResult()
    data class Failure(val error: Error): FaqResult()
}