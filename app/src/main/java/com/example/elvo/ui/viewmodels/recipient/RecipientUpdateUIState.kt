package com.example.elvo.ui.viewmodels.recipient

sealed class RecipientUpdateUIState {
    data object Success: RecipientUpdateUIState()
    data class Error(val errorResId: Int): RecipientUpdateUIState()
    data object RequiredFieldsAreEmpty: RecipientUpdateUIState()
    data object NothingChanged: RecipientUpdateUIState()
    data object Unauthorized: RecipientUpdateUIState()
}