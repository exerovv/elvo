package com.example.elvo.data.network.implementations

import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.recipient.RecipientRequest
import com.example.elvo.data.network.services.RecipientService
import com.example.elvo.data.utils.ErrorParser
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.domain.repositories.RecipientRepository
import retrofit2.HttpException
import javax.inject.Inject

class RecipientRepositoryImpl @Inject constructor(
    private val recipientService: RecipientService
): RecipientRepository {
    override suspend fun addRecipient(recipientRequest: Recipient): RecipientResult<Nothing> {
        return try{
            recipientService.addRecipient(RecipientRequest(
                recipientRequest.name!!,
                recipientRequest.surname!!,
                recipientRequest.patronymic,
                recipientRequest.phone!!,
                recipientRequest.city!!,
                recipientRequest.street!!,
                recipientRequest.house!!.toInt(),
                recipientRequest.building,
                recipientRequest.flat!!.toInt(),
                recipientRequest.floor!!.toInt()
            ))
            RecipientResult.Success()
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            return RecipientResult.Failure(error.toDomain())
        }
    }

    override suspend fun fetchRecipientList(): RecipientResult<List<RecipientShort>> {
        return try{
            val result = recipientService.fetchRecipientList()
            RecipientResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            return RecipientResult.Failure(error.toDomain())
        }
    }

    override suspend fun fetchSingleRecipient(id: Int): RecipientResult<RecipientFull> {
        return try{
            val result = recipientService.fetchSingleRecipient(id)
            RecipientResult.Success(result.toDomain())
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            return RecipientResult.Failure(error.toDomain())
        }
    }

    override suspend fun updateRecipient(id: Int): RecipientResult<Nothing> {
        return try{
            recipientService.updateRecipient(id)
            RecipientResult.Success()
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = ErrorParser.parseError(errorBody)
            return RecipientResult.Failure(error.toDomain())
        }
    }
}