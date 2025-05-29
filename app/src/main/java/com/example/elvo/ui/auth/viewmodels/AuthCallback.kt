package com.example.elvo.ui.auth.viewmodels

interface AuthCallback {
    fun onSuccess()
    fun onError(error: String)
}