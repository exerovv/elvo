package com.example.elvo.ui.viewmodels.order

import com.example.elvo.domain.model.order.OrderFullInfo

sealed class SingleOrderUIState {
    data class Success(val data: OrderFullInfo): SingleOrderUIState()
    data class Error(val errorResId: Int): SingleOrderUIState()
    data object Default: SingleOrderUIState()
    data object Unauthorized: SingleOrderUIState()
}