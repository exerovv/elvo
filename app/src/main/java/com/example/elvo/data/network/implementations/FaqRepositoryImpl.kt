package com.example.elvo.data.network.implementations

import com.example.elvo.data.database.RoomAppDataSource
import com.example.elvo.data.database.converters.toDomain
import com.example.elvo.data.database.converters.toEntity
import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.services.FaqService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.model.faq.FaqResult
import com.example.elvo.domain.repositories.FaqRepository
import retrofit2.HttpException
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val faqService: FaqService,
    private val appDataSource: RoomAppDataSource
): FaqRepository {
    override suspend fun fetchFaq(): FaqResult {
        return try{
            val cachedFaqs = appDataSource.faqDao().getAllFaq().toDomain()

            if (cachedFaqs.isNotEmpty()) {
               return FaqResult.Success(data = cachedFaqs)
            }

            val data = faqService.fetchFaq()
            val entities = data.toEntity()


            appDataSource.faqDao().insertFaqList(entities)
            val updatedCachedFaqs = appDataSource.faqDao().getAllFaq().toDomain()
            FaqResult.Success(data = updatedCachedFaqs)
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            FaqResult.Failure(error.toDomain())
        }
    }
}