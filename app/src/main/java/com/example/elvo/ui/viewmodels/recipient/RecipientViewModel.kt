package com.example.elvo.ui.viewmodels.recipient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elvo.R
import com.example.elvo.domain.enums.ErrorCodes
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientResult
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.domain.usecase.recipient.AddRecipientUseCase
import com.example.elvo.domain.usecase.recipient.FetchRecipientListUseCase
import com.example.elvo.domain.usecase.recipient.FetchSingleRecipientUseCase
import com.example.elvo.domain.usecase.recipient.UpdateRecipientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipientViewModel @Inject constructor(
    private val addRecipientUseCase: AddRecipientUseCase,
    private val fetchRecipientListUseCase: FetchRecipientListUseCase,
    private val fetchSingleRecipientUseCase: FetchSingleRecipientUseCase,
    private val updateRecipientUseCase: UpdateRecipientUseCase,
) : ViewModel() {
    private val _listState: MutableStateFlow<RecipientListUIState> =
        MutableStateFlow(RecipientListUIState.Default)
    val listState
        get() = _listState.asStateFlow()

    private val _singleRecipientState: MutableStateFlow<SingleRecipientUIState> =
        MutableStateFlow(SingleRecipientUIState.Default)
    val singleRecipientState
        get() = _singleRecipientState.asStateFlow()

    private val _addState: MutableSharedFlow<RecipientAddUIState> = MutableSharedFlow()
    val addState
        get() = _addState.asSharedFlow()

    private val _updateState: MutableSharedFlow<RecipientUpdateUIState> = MutableSharedFlow()
    val updateState
        get() = _updateState.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchRecipients()
        }
    }

    suspend fun addRecipient(recipient: Recipient) {
        when (val result = addRecipientUseCase(recipient)) {
            is RecipientResult.Success -> {
                _addState.emit(RecipientAddUIState.Success(result.data!!))
            }
            is RecipientResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.REQUIRED_FIELD_EMPTY) {
                    _addState.emit(RecipientAddUIState.RequiredFieldsAreEmpty)
                    return
                }
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _addState.emit(RecipientAddUIState.Unauthorized)
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.INCORRECT_NAME -> R.string.incorrect_name
                    ErrorCodes.INCORRECT_PHONE -> R.string.incorrect_phone
                    ErrorCodes.INCORRECT_ADDRESS -> R.string.incorrect_address
                    else -> R.string.unknown_error
                }
                _addState.emit(RecipientAddUIState.Error(errorResId))
            }
        }
    }

    suspend fun updateRecipient(id: Int, newRecipient: Recipient, oldRecipient: Recipient) {
        when (val result = updateRecipientUseCase(id, newRecipient, oldRecipient)) {
            is RecipientResult.Success -> _updateState.emit(RecipientUpdateUIState.Success(result.data))
            is RecipientResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.REQUIRED_FIELD_EMPTY) {
                    _updateState.emit(RecipientUpdateUIState.RequiredFieldsAreEmpty)
                    return
                }
                if (result.error.errorCode == ErrorCodes.NOTHING_CHANGED) {
                    _updateState.emit(RecipientUpdateUIState.NothingChanged)
                    return
                }
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _updateState.emit(RecipientUpdateUIState.Unauthorized)
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.INCORRECT_NAME -> R.string.incorrect_name
                    ErrorCodes.INCORRECT_PHONE -> R.string.incorrect_phone
                    ErrorCodes.INCORRECT_ADDRESS -> R.string.incorrect_address
                    ErrorCodes.ADDRESS_NOT_FOUND -> R.string.address_not_found
                    else -> R.string.unknown_error
                }
                _updateState.emit(RecipientUpdateUIState.Error(errorResId))
            }
        }
    }

    suspend fun fetchSingleRecipient(id: Int){
        when (val result = fetchSingleRecipientUseCase(id)) {
            is RecipientResult.Success<RecipientFull> -> _singleRecipientState.value =
                SingleRecipientUIState.Success(result.data!!)
            is RecipientResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _singleRecipientState.value = SingleRecipientUIState.Unauthorized
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    ErrorCodes.RECIPIENT_NOT_FOUND -> R.string.recipient_not_found
                    else -> R.string.unknown_error
                }
                _singleRecipientState.value = SingleRecipientUIState.Error(errorResId)
            }

        }
    }

    suspend fun fetchRecipients() {
        when (val result = fetchRecipientListUseCase()) {
            is RecipientResult.Success<List<RecipientShort>> -> _listState.value =
                RecipientListUIState.Success(result.data!!)
            is RecipientResult.Failure -> {
                if (result.error.errorCode == ErrorCodes.UNAUTHORIZED) {
                    _listState.value = RecipientListUIState.Unauthorized
                    return
                }
                val errorResId = when (result.error.errorCode) {
                    ErrorCodes.SERVER_ERROR -> R.string.server_error
                    else -> R.string.unknown_error
                }
                _listState.value = RecipientListUIState.Error(errorResId)
            }
        }
    }
}