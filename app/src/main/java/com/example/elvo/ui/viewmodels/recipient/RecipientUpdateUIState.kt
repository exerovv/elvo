package com.example.elvo.ui.viewmodels.recipient

import com.example.elvo.domain.model.recipient.RecipientShort

sealed class RecipientUpdateUIState {
    data class Success(val data: RecipientShort?): RecipientUpdateUIState()
    data class Error(val errorResId: Int): RecipientUpdateUIState()
    data object RequiredFieldsAreEmpty: RecipientUpdateUIState()
    data object NothingChanged: RecipientUpdateUIState()
    data object Unauthorized: RecipientUpdateUIState()
}