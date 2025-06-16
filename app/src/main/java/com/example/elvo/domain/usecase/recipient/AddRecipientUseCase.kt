package com.example.elvo.domain.usecase.recipient

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.repositories.RecipientRepository
import com.example.elvo.utils.RecipientValidator
import javax.inject.Inject

class AddRecipientUseCase @Inject constructor(
    private val recipientRepository: RecipientRepository
) {
    suspend operator fun invoke(
        recipient: Recipient
    ): RecipientResult<Unit> {
        val validationResult =
            RecipientValidator.validateRequiredFields(recipient)

        if (!validationResult) return RecipientResult.Failure(Error(ErrorCodes.REQUIRED_FIELD_EMPTY))

        return recipientRepository.addRecipient(
            recipient
        )
    }
}