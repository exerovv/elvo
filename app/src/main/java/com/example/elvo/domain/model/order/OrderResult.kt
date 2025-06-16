package com.example.elvo.domain.model.order

import com.example.elvo.domain.model.Error

sealed class OrderResult<out T> {
    data class Success<T>(val data: T) : OrderResult<T>()
    data class Failure(val error: Error) : OrderResult<Nothing>()
}