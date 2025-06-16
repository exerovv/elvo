package com.example.elvo.domain.usecase.order

import com.example.elvo.domain.repositories.OrderRepository
import javax.inject.Inject

class FetchSingleOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(id: Int) = orderRepository.fetchSingleOrder(id)
}