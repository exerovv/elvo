package com.example.elvo.ui.auth.viewmodels

sealed class AuthUIEvent {
    data class SingUpLoginChanged(val value: String) : AuthUIEvent()
    data class SingUpPasswordChanged(val value: String) : AuthUIEvent()
    object SignUp : AuthUIEvent()

    data class SingInLoginChanged(val value: String) : AuthUIEvent()
    data class SingInPasswordChanged(val value: String) : AuthUIEvent()
    object SignIn : AuthUIEvent()
}