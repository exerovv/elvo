package com.example.elvo.data.network.implementations

import android.util.Log
import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.services.PopularService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.model.popular.PopularResult
import com.example.elvo.domain.repositories.PopularRepository
import retrofit2.HttpException
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(
    private val popularService: PopularService
): PopularRepository {
    override suspend fun fetchPopularItems(): PopularResult {
        try{
            val result = popularService.fetchPopularItems()

            return PopularResult.Success(result.toDomain())

        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            return PopularResult.Failure(error.toDomain())
        }
    }
}