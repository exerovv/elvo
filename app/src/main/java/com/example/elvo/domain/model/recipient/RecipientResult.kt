package com.example.elvo.domain.model.recipient

import com.example.elvo.domain.model.Error

sealed class  RecipientResult<out T> {
    data class Success<T>(val data: T?): RecipientResult<T>()
    data class Failure(val error: Error): RecipientResult<Nothing>()
}