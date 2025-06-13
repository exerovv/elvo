package com.example.elvo.ui.viewmodels.user

import com.example.elvo.domain.model.user.ProfileInfo

sealed class UserUIState {
    data class Success(val userInfo: ProfileInfo): UserUIState()
    data class Error(val errorResId: Int): UserUIState()
    data object Default: UserUIState()
}