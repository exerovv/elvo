package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.order.OrderAddRequest
import com.example.elvo.data.network.services.OrderService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.order.OrderFullInfo
import com.example.elvo.domain.model.order.OrderResult
import com.example.elvo.domain.model.order.OrderShort
import com.example.elvo.domain.model.order.OrderStatus
import com.example.elvo.domain.model.order.Payment
import com.example.elvo.domain.repositories.OrderRepository
import retrofit2.HttpException
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderService: OrderService): OrderRepository {
    override suspend fun fetchOrderList(): OrderResult<List<OrderShort>> {
        return try{
            val result = orderService.fetchOrderList()
            OrderResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            OrderResult.Failure(error.toDomain())
        }
    }

    override suspend fun fetchSingleOrder(id: Int): OrderResult<OrderFullInfo> {
        return try{
            val result = orderService.fetchSingleOrder(id)
            OrderResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            OrderResult.Failure(error.toDomain())
        }
    }

    override suspend fun addOrder(order: Order): OrderResult<OrderShort> {
        return try{
            val result = orderService.addOrder(
                OrderAddRequest(
                    order.recipientId!!,
                    order.orderName!!,
                    order.trackNumber!!,
                    order.ruDescription!!,
                    order.chDescription!!,
                    order.link!!,
                    order.itemPrice!!
                )
            )
            OrderResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            OrderResult.Failure(error.toDomain())
        }
    }

    override suspend fun fetchOrderHistory(id: Int): OrderResult<List<OrderStatus>> {
        return try{
            val result = orderService.fetchOrderHistory(id)
            OrderResult.Success(result.map{ it.toDomain() })
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            OrderResult.Failure(error.toDomain())
        }
    }

    override suspend fun payForOrder(id: Int): OrderResult<Payment> {
        return try{
            val result = orderService.payForOrder(id)
            OrderResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            OrderResult.Failure(error.toDomain())
        }
    }
}