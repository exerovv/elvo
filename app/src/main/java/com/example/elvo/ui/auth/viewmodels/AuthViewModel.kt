package com.example.elvo.ui.auth.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elvo.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    fun onEvent(event: AuthUIEvent){

    }
}