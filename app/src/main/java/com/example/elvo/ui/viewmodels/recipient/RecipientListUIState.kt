package com.example.elvo.ui.viewmodels.recipient

import com.example.elvo.domain.model.recipient.RecipientShort

sealed class RecipientListUIState {
    data class Success(val data: List<RecipientShort>): RecipientListUIState()
    data class Error(val errorResId: Int): RecipientListUIState()
    data object Default: RecipientListUIState()
    data object Unauthorized: RecipientListUIState()
}