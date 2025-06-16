package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.order.OrderFullInfo
import com.example.elvo.domain.model.order.OrderResult
import com.example.elvo.domain.model.order.OrderShort
import com.example.elvo.domain.model.order.OrderStatus
import com.example.elvo.domain.model.order.Payment

interface OrderRepository {
    suspend fun fetchOrderList(): OrderResult<List<OrderShort>>
    suspend fun fetchSingleOrder(id: Int): OrderResult<OrderFullInfo>
    suspend fun addOrder(order: Order): OrderResult<OrderShort>
    suspend fun fetchOrderHistory(id: Int): OrderResult<List<OrderStatus>>
    suspend fun payForOrder(id: Int): OrderResult<Payment>
}