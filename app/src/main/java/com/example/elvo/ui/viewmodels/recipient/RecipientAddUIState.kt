package com.example.elvo.ui.viewmodels.recipient

import com.example.elvo.domain.model.recipient.RecipientShort

sealed class RecipientAddUIState {
    data class Success(val data: RecipientShort): RecipientAddUIState()
    data class Error(val errorResId: Int): RecipientAddUIState()
    data object RequiredFieldsAreEmpty: RecipientAddUIState()
    data object Unauthorized: RecipientAddUIState()
}