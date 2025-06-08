package com.example.elvo.domain.usecase.recipient

import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.domain.repositories.RecipientRepository
import javax.inject.Inject

class FetchRecipientListUseCase @Inject constructor(
    private val recipientRepository: RecipientRepository
) {
    suspend operator fun invoke(): RecipientResult<List<RecipientShort>> =
        recipientRepository.fetchRecipientList()
}