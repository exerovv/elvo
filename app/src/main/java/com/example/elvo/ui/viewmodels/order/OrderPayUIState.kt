package com.example.elvo.ui.viewmodels.order

import com.example.elvo.domain.model.order.Payment

sealed class OrderPayUIState {
    data class Success(val data: Payment): OrderPayUIState()
    data class Error(val errorResId: Int): OrderPayUIState()
    data object ImpossibleToPay: OrderPayUIState()
    data object Unauthorized: OrderPayUIState()
}