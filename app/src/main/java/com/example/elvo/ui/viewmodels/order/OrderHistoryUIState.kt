package com.example.elvo.ui.viewmodels.order

import com.example.elvo.domain.model.order.OrderStatus

sealed class OrderHistoryUIState {
    data class Success(val data: List<OrderStatus>): OrderHistoryUIState()
    data class Error(val errorResId: Int): OrderHistoryUIState()
    data object Default: OrderHistoryUIState()
    data object Unauthorized: OrderHistoryUIState()
}