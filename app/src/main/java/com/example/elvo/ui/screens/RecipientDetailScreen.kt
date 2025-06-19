package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.R
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.recipient.RecipientUpdateUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import com.example.elvo.ui.viewmodels.recipient.SingleRecipientUIState
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RecipientDetailScreen(navController: NavController,recipientId: Int, recipientViewModel: RecipientViewModel = hiltViewModel()) {
    val singleState = recipientViewModel.singleRecipientState
    val updateState = recipientViewModel.updateState
    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val originalRecipient = remember { mutableStateOf<RecipientFull?>(null) }

    LaunchedEffect(recipientId) {
        recipientViewModel.fetchSingleRecipient(recipientId)
    }

    LaunchedEffect(singleState) {
        singleState.collectLatest { state ->
            when (state) {
                is SingleRecipientUIState.Success -> {
                    val recipient = state.data
                    name = recipient.fullName
                    phone = recipient.phone
                    address = recipient.address
                    originalRecipient.value = recipient
                }

                is SingleRecipientUIState.Error -> {
                    Toast
                        .makeText(context, context.getString(state.errorResId), Toast.LENGTH_LONG)
                        .show()
                }

                is SingleRecipientUIState.Unauthorized -> {
                }

                SingleRecipientUIState.Default -> {}
            }
        }
    }

    LaunchedEffect(updateState) {
        updateState.collectLatest { state ->
            when (state) {
                is RecipientUpdateUIState.Success -> {
                    Toast.makeText(context,
                        context.getString(R.string.data_updated), Toast.LENGTH_SHORT).show()
                    isEditing = false
                    recipientViewModel.fetchSingleRecipient(recipientId)
                }

                is RecipientUpdateUIState.RequiredFieldsAreEmpty -> {
                    Toast.makeText(context, context.getString(R.string.required_fields), Toast.LENGTH_LONG).show()
                }

                is RecipientUpdateUIState.NothingChanged -> {
                    Toast.makeText(context,
                        context.getString(R.string.No_changes_detected), Toast.LENGTH_LONG).show()
                }

                is RecipientUpdateUIState.Unauthorized -> {
                    navController.navigate(Screen.Login.route) {
                    }
                }

                is RecipientUpdateUIState.Error -> {
                    Toast.makeText(context, context.getString(state.errorResId), Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(Color(0xFFF7FAFC))
        ) {
            Text(
                stringResource(R.string.Data),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecipientField(label = stringResource(R.string.name), value = name, isEditing = isEditing) { name = it }
            RecipientField(label = stringResource(R.string.phone_number), value = phone, isEditing = isEditing) { phone = it }
            RecipientField(label = stringResource(R.string.address), value = address, isEditing = isEditing) { address = it }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("recipient_edit/$recipientId")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(stringResource(R.string.Edit), color = Color.White)
            }
        }
    }
}

@Composable
fun RecipientField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
        } else {
            Text(value, style = MaterialTheme.typography.bodyLarge)
        }

        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}
