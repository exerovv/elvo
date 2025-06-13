package com.example.elvo.domain.model.user

import com.example.elvo.domain.model.Error

sealed class ProfileResult {
    data class Success(val data: ProfileInfo): ProfileResult()
    data class Failure(val error: Error): ProfileResult()
}