package com.example.elvo.utils

import com.example.elvo.domain.model.recipient.Recipient

object RecipientValidator {
    fun validateRequiredFields(
        recipient: Recipient
    ): Boolean {
        return !(recipient.name.isNullOrBlank() ||
                recipient.surname.isNullOrBlank() ||
                recipient.phone.isNullOrBlank() ||
                recipient.city.isNullOrBlank() ||
                recipient.street.isNullOrBlank() ||
                recipient.house.isNullOrEmpty() ||
                recipient.flat.isNullOrEmpty() ||
                recipient.floor.isNullOrEmpty())
    }

    fun fieldsChanged(
        oldRecipient: Recipient,
        newRecipient: Recipient
    ): Boolean {
        return oldRecipient != newRecipient
    }
}