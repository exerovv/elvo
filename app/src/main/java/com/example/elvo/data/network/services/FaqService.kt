package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.faq.FaqDTO
import retrofit2.http.GET

interface FaqService {
    @GET("faq")
    suspend fun fetchFaq(): List<FaqDTO>
}