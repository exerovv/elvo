package com.example.elvo.ui.viewmodels.order

sealed class OrderAddUIState {
    data object Success: OrderAddUIState()
    data class Error(val errorResId: Int): OrderAddUIState()
    data object RequiredFieldsAreEmpty: OrderAddUIState()
    data object IncorrectLink: OrderAddUIState()
    data object Unauthorized: OrderAddUIState()
}