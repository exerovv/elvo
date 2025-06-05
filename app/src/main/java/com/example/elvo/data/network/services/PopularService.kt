package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.popular.PopularItemsDTO
import retrofit2.http.GET

interface PopularService {
    @GET("popular")
    fun fetchPopularItems(): List<PopularItemsDTO>
}