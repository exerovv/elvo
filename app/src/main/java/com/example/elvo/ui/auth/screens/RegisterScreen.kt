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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elvo.R
import com.example.elvo.ui.auth.viewmodels.AuthUIState
import com.example.elvo.ui.auth.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(onLoginSuccess: () -> Unit, authViewModel: AuthViewModel = hiltViewModel()) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }

    val authUIState = authViewModel.authUIState
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        authUIState.collect{ state ->
            when(state) {
                is AuthUIState.Success -> onLoginSuccess()
                is AuthUIState.Loading -> Toast.makeText(context,
                    context.getString(R.string.state_loading), Toast.LENGTH_SHORT).show()
                is AuthUIState.Error -> Toast.makeText(context, state.errorResId, Toast.LENGTH_LONG).show()
                else -> {}
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0B57D0),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF0B57D0),
                    focusedLabelColor = Color(0xFF0B57D0),
                    unfocusedLabelColor = Color.Gray
                )

                OutlinedTextField(
                    value = login,
                    onValueChange = {login = it },
                    label = { Text(stringResource(R.string.login))  },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors

                    )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it },
                    label = { Text(stringResource(R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                    )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmationPassword,
                    onValueChange = {confirmationPassword = it },
                    label = { Text(stringResource(R.string.password_confirmation)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                    )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { authViewModel.register(login, password, confirmationPassword)},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(stringResource(R.string.signup), color = Color.White)
                }
            }
            TextButton(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    stringResource(R.string.already_account),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
    }
}



