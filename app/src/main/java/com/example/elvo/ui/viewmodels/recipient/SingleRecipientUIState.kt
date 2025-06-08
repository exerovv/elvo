package com.example.elvo.ui.viewmodels.recipient

import com.example.elvo.domain.model.recipient.RecipientFull

sealed class SingleRecipientUIState {
    data class Success(val data: RecipientFull): SingleRecipientUIState()
    data class Error(val errorResId: Int): SingleRecipientUIState()
    data object Default: SingleRecipientUIState()
    data object Unauthorized: SingleRecipientUIState()
}