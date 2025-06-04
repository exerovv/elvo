package com.example.elvo.utils

object Validator {
    fun loginValidation(login: String, password: String) : AuthValidationResult{
        if (!validateLogin(login)) return AuthValidationResult.InvalidLogin
        if (!validatePassword(password)) return AuthValidationResult.InvalidPassword
        return AuthValidationResult.Success
    }

    fun registerValidation(login: String, password: String, confirmationPassword: String): AuthValidationResult{
        if (!validateLogin(login)) return AuthValidationResult.InvalidLogin
        if (!validatePassword(password)) return AuthValidationResult.InvalidPassword
        if(!validateConfirmingPassword(password, confirmationPassword)) return AuthValidationResult.InvalidConfirmingPassword
        return AuthValidationResult.Success
    }

    private fun validateLogin(login: String): Boolean{
        return login.isNotBlank()
    }

    private fun validatePassword(password: String): Boolean{
        return password.isNotBlank() && ("^(?=[a-zA-Z0-9#@$?!^&*()]{8,16}$)(?=.*?[a-zA-z])(?=.*?[0-9]).*".toRegex().matches(password))
    }

    private fun validateConfirmingPassword(password: String, confirmingPassword: String): Boolean{
        return password == confirmingPassword
    }
}