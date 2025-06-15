package com.example.elvo.data.network.services

import com.example.elvo.data.network.models.recipient.RecipientFullDTO
import com.example.elvo.data.network.models.recipient.RecipientRequest
import com.example.elvo.data.network.models.recipient.RecipientShortDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipientService {
    @POST("recipient/add")
    suspend fun addRecipient(@Body recipientRequest: RecipientRequest)

    @GET("recipient/get")
    suspend fun fetchRecipientList(): List<RecipientShortDTO>

    @GET("recipient/get/{id}")
    suspend fun fetchSingleRecipient(@Path("id") id: Int): RecipientFullDTO

    @PUT("update/{id}")
    suspend fun updateRecipient(@Path("id") id: Int)
}