package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.popular.PopularResult

interface PopularRepository {
    suspend fun fetchPopularItems(): PopularResult
}