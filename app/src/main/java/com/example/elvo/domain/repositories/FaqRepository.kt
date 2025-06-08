package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.faq.FaqResult

interface FaqRepository {
    suspend fun fetchFaq(): FaqResult
}