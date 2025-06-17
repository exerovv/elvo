package com.example.elvo.domain.usecase.order

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.order.OrderResult
import com.example.elvo.domain.model.order.OrderShort
import com.example.elvo.domain.repositories.OrderRepository
import com.example.elvo.utils.OrderValidator
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(order: Order): OrderResult<OrderShort>{
        val validationDataResult = OrderValidator.validateAllFields(order)
        val validationLinkResult = OrderValidator.validateLink(order.link)
        if (!validationDataResult) return OrderResult.Failure(Error(ErrorCodes.INCORRECT_ORDER_DATA))
        if (validationLinkResult) return OrderResult.Failure(Error(ErrorCodes.INCORRECT_LINK))

        return orderRepository.addOrder(
            order
        )
    }
}