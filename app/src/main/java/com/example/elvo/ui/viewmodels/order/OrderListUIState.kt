package com.example.elvo.ui.viewmodels.order

import com.example.elvo.domain.model.order.OrderShort

sealed class OrderListUIState {
    data class Success(val data: List<OrderShort>): OrderListUIState()
    data class Error(val errorResId: Int): OrderListUIState()
    data object Default: OrderListUIState()
    data object Unauthorized: OrderListUIState()
}