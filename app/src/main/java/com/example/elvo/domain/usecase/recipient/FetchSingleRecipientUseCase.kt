package com.example.elvo.domain.usecase.recipient

import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.repositories.RecipientRepository
import javax.inject.Inject

class FetchSingleRecipientUseCase @Inject constructor(
    private val recipientRepository: RecipientRepository
) {
    suspend operator fun invoke(id: Int): RecipientResult<RecipientFull> =
        recipientRepository.fetchSingleRecipient(id)
}