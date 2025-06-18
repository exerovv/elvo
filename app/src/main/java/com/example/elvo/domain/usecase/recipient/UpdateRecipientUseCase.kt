package com.example.elvo.domain.usecase.recipient

import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.Error
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.domain.repositories.RecipientRepository
import com.example.elvo.utils.RecipientValidator
import javax.inject.Inject

class UpdateRecipientUseCase @Inject constructor(
    private val recipientRepository: RecipientRepository
) {
    suspend operator fun invoke(id: Int, newRecipient: Recipient, oldRecipient: Recipient): RecipientResult<RecipientShort> {
        val requiredValidation = RecipientValidator.validateRequiredFields(newRecipient)
        val changedValidation = RecipientValidator.fieldsChanged(
                oldRecipient = oldRecipient,
                newRecipient = newRecipient
            )
        if(!requiredValidation) return RecipientResult.Failure(Error(ErrorCodes.REQUIRED_FIELD_EMPTY))
        if(!changedValidation) return RecipientResult.Failure(Error(ErrorCodes.NOTHING_CHANGED))
        return recipientRepository.updateRecipient(newRecipient, id)
    }
}