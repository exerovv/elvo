package com.example.elvo.domain.usecase.popular

import com.example.elvo.domain.model.popular.PopularResult
import com.example.elvo.domain.repositories.PopularRepository
import javax.inject.Inject

class FetchPopularItemsUseCase @Inject constructor(
    private val popularRepository: PopularRepository
) {
    suspend operator fun invoke(): PopularResult = popularRepository.fetchPopularItems()
}