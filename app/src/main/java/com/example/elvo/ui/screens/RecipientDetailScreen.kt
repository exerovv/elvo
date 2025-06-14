package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.recipient.RecipientUpdateUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import com.example.elvo.ui.viewmodels.recipient.SingleRecipientUIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun RecipientDetailScreen(navController: NavController,recipientId: Int, recipientViewModel: RecipientViewModel = hiltViewModel()) {
    val singleState = recipientViewModel.singleRecipientState
    val updateState = recipientViewModel.updateState
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var originalRecipient = remember { mutableStateOf<RecipientFull?>(null) }

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
                    Toast
                        .makeText(context, "Вы не авторизованы", Toast.LENGTH_LONG).show()
                }

                SingleRecipientUIState.Default -> {}
            }
        }
    }

    LaunchedEffect(updateState) {
        updateState.collectLatest { state ->
            when (state) {
                is RecipientUpdateUIState.Success -> {
                    Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show()
                    isEditing = false
                    recipientViewModel.fetchSingleRecipient(recipientId)
                }

                is RecipientUpdateUIState.RequiredFieldsAreEmpty -> {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
                }

                is RecipientUpdateUIState.NothingChanged -> {
                    Toast.makeText(context, "Изменения не обнаружены", Toast.LENGTH_LONG).show()
                }

                is RecipientUpdateUIState.Unauthorized -> {
                    Toast.makeText(context, "Вы не авторизованы", Toast.LENGTH_LONG).show()
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
                "Данные",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecipientField(label = "Имя", value = name, isEditing = isEditing) { name = it }
            RecipientField(label = "Номер телефона", value = phone, isEditing = isEditing) { phone = it }
            RecipientField(label = "Адрес", value = address, isEditing = isEditing) { address = it }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (isEditing) {
                        originalRecipient.value?.let { old ->
                            val updatedRecipient = old.copy(
                                fullName = name.trim(),
                                phone = phone.trim(),
                                address = address.trim()
                            )
                        }
                    }
                    isEditing = !isEditing
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(if (isEditing) "Сохранить" else "Редактировать", color = Color.White)
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
