package com.example.elvo.ui.viewmodels.recipient

sealed class RecipientAddUIState {
    data object Success: RecipientAddUIState()
    data class Error(val errorResId: Int): RecipientAddUIState()
    data object RequiredFieldsAreEmpty: RecipientAddUIState()
    data object Unauthorized: RecipientAddUIState()
}