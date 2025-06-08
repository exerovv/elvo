package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.services.FaqService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.model.faq.FaqResult
import com.example.elvo.domain.repositories.FaqRepository
import retrofit2.HttpException
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val faqService: FaqService
): FaqRepository {
    override suspend fun fetchFaq(): FaqResult {
        return try{
            val data = faqService.fetchFaq()
            FaqResult.Success(data = data.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            FaqResult.Failure(error.toDomain())
        }
    }
}