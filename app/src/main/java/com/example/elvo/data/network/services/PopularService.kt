package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.popular.PopularItemDTO
import retrofit2.http.GET

interface PopularService {
    @GET("popular")
    suspend fun fetchPopularItems(): List<PopularItemDTO>
}