package com.example.elvo.ui.viewmodels.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.order.OrderFullInfo
import com.example.elvo.domain.model.order.OrderResult
import com.example.elvo.domain.model.order.OrderShort
import com.example.elvo.domain.model.order.OrderStatus
import com.example.elvo.domain.model.order.Payment
import com.example.elvo.domain.usecase.order.AddOrderUseCase
import com.example.elvo.domain.usecase.order.FetchOrderHistoryUseCase
import com.example.elvo.domain.usecase.order.FetchOrderListUseCase
import com.example.elvo.domain.usecase.order.FetchSingleOrderUseCase
import com.example.elvo.domain.usecase.order.PayForOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val addOrderUseCase: AddOrderUseCase,
    private val fetchOrderHistoryUseCase: FetchOrderHistoryUseCase,
    private val fetchOrderListUseCase: FetchOrderListUseCase,
    private val fetchSingleOrderUseCase: FetchSingleOrderUseCase,
    private val payForOrderUseCase: PayForOrderUseCase,
) : ViewModel() {
    private val _listState: MutableStateFlow<OrderListUIState> =
        MutableStateFlow(OrderListUIState.Default)
    val listState
        get() = _listState.asStateFlow()

    private val _historyState: MutableStateFlow<OrderHistoryUIState> =
        MutableStateFlow(OrderHistoryUIState.Default)
    val historyState
        get() = _historyState.asStateFlow()

    private val _singleOrderState: MutableStateFlow<SingleOrderUIState> =
        MutableStateFlow(SingleOrderUIState.Default)
    val singleOrderState
        get() = _singleOrderState.asStateFlow()

    private val _addState: MutableSharedFlow<OrderAddUIState> = MutableSharedFlow()
    val addState
        get() = _addState.asSharedFlow()

    private val _paymentState: MutableSharedFlow<OrderPayUIState> = MutableSharedFlow()
    val paymentState
        get() = _paymentState.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchOrders()
        }
    }

    suspend fun addOrder(order: Order) {
        when (val result = addOrderUseCase(order)) {
            is OrderResult.Success -> {
                _addState.emit(OrderAddUIState.Success)
            }

            is OrderResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.INCORRECT_ORDER_DATA) {
                    _addState.emit(OrderAddUIState.RequiredFieldsAreEmpty)
                    return
                }
                if (result.error.errorCode == ErrorCodes.INCORRECT_LINK) {
                    _addState.emit(OrderAddUIState.IncorrectLink)
                    return
                }
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _addState.emit(OrderAddUIState.Unauthorized)
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.INCORRECT_LINK -> R.string.incorrect_link
                    ErrorCodes.INCORRECT_CREDENTIALS -> R.string.incorrect_credentials
                    else -> R.string.unknown_error
                }
                _addState.emit(OrderAddUIState.Error(errorResId))
            }
        }
    }

    suspend fun fetchSingleOrder(id: Int) {
        when (val result = fetchSingleOrderUseCase(id)) {
            is OrderResult.Success<OrderFullInfo> -> _singleOrderState.value =
                SingleOrderUIState.Success(result.data)

            is OrderResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _singleOrderState.value = SingleOrderUIState.Unauthorized
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.ORDER_NOT_FOUND -> R.string.order_not_found
                    else -> R.string.unknown_error
                }
                _singleOrderState.value = SingleOrderUIState.Error(errorResId)
            }

        }
    }

    suspend fun fetchOrders() {
        when (val result = fetchOrderListUseCase()) {
            is OrderResult.Success<List<OrderShort>> -> _listState.value =
                OrderListUIState.Success(result.data)

            is OrderResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _listState.value = OrderListUIState.Unauthorized
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    else -> R.string.unknown_error
                }
                _listState.value = OrderListUIState.Error(errorResId)
            }
        }
    }

    suspend fun payForOrder(id: Int) {
        when (val result = payForOrderUseCase(id)) {
            is OrderResult.Success<Payment> -> _paymentState.emit(OrderPayUIState.Success(result.data))
            is OrderResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _paymentState.emit(OrderPayUIState.Unauthorized)
                    return
                }
                if (result.error.errorCode == ErrorCodes.IMPOSSIBLE_TO_PAY) {
                    _paymentState.emit(OrderPayUIState.ImpossibleToPay)
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.ORDER_NOT_FOUND -> R.string.order_not_found
                    else -> R.string.unknown_error
                }
                _paymentState.emit(OrderPayUIState.Error(errorResId))
            }
        }
    }

    suspend fun fetchOrderHistory(id: Int) {
        when (val result = fetchOrderHistoryUseCase(id)) {
            is OrderResult.Success<List<OrderStatus>> -> _historyState.value =
                OrderHistoryUIState.Success(result.data)

            is OrderResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _historyState.value = OrderHistoryUIState.Unauthorized
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    else -> R.string.unknown_error
                }
                _historyState.value = OrderHistoryUIState.Error(errorResId)
            }
        }
    }
}