package com.example.elvo.domain.repositories

import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.model.recipient.RecipientShort

interface RecipientRepository {
    suspend fun addRecipient(recipientRequest: Recipient): RecipientResult<RecipientShort>
    suspend fun fetchRecipientList(): RecipientResult<List<RecipientShort>>
    suspend fun fetchSingleRecipient(id: Int): RecipientResult<RecipientFull>
    suspend fun updateRecipient(updateRequest: Recipient, id: Int): RecipientResult<RecipientShort>
}