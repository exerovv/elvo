package com.example.elvo.ui.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elvo.data.implementations.AuthServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authServiceImpl: AuthServiceImpl
) : ViewModel() {

//    private val _authState = MutableLiveData<AuthState>(AuthState.Default)
//    val authState: LiveData<AuthState> get() = _authState
//
//    fun login(username: String, password: String) {
//        if (!isValidPassword(password)) {
//            _authState.postValue(AuthState.Error("Invalid password"))
//            return
//        }
//
//        _authState.postValue(AuthState.Loading)
//
//        authServiceImpl.signIn(username, password, object : AuthCallback {
//            override fun onSuccess() {
//                _authState.postValue(AuthState.Success)
//            }
//
//            override fun onError(error: String) {
//                _authState.postValue(AuthState.Error(error))
//            }
//        })
//    }



    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}