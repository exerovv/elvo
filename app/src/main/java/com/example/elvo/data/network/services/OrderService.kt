package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.order.OrderAddRequest
import com.example.elvo.data.network.models.order.OrderFullInfoDTO
import com.example.elvo.data.network.models.order.OrderShortDTO
import com.example.elvo.data.network.models.order.OrderStatusDTO
import com.example.elvo.data.network.models.order.PaymentDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("order/list")
    suspend fun fetchOrderList(): List<OrderShortDTO>

    @GET("order/{id}")
    suspend fun fetchSingleOrder(@Path("id") id: Int): OrderFullInfoDTO

    @POST("order/add")
    suspend fun addOrder(@Body order: OrderAddRequest): OrderShortDTO

    @GET("order/history/{id}")
    suspend fun fetchOrderHistory(@Path("id") id: Int): List<OrderStatusDTO>

    @POST("order/pay/{id}")
    suspend fun payForOrder(@Path("id") id: Int): PaymentDTO
}