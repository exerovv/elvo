package com.example.elvo.ui.auth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elvo.R
import com.example.elvo.ui.auth.viewmodels.AuthUIState
import com.example.elvo.ui.auth.viewmodels.AuthViewModel
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit, authViewModel: AuthViewModel = hiltViewModel()) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authUIState = authViewModel.authUIState
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        authUIState.collect{ state ->
            when(state) {
                is AuthUIState.Success -> {
                    onLoginSuccess()}
                is AuthUIState.Loading -> isLoading = true
                is AuthUIState.Error -> Toast.makeText(context, state.errorResId, Toast.LENGTH_LONG).show()
                is AuthUIState.Unauthorized -> {}
                is AuthUIState.UnknownError -> {Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_LONG).show()}
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Вход", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(32.dp))


                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text(stringResource(R.string.login)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    enabled = !isLoading

                    )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it },
                    label = { Text(stringResource(R.string.password))},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = textFieldColors,
                    enabled = !isLoading

                    )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { authViewModel.login(login, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    enabled = !isLoading
                ) {
                    Text(stringResource(R.string.login_button), color = Color.White)
                }
            }

            TextButton(
                onClick = { onRegisterClick() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    stringResource(R.string.sigiup_button),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
    }
}



