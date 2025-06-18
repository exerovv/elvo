package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.R
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.recipient.RecipientAddUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun RecipientAddScreen(navController: NavController, recipientViewModel: RecipientViewModel = hiltViewModel()) {

    val state = recipientViewModel.addState
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var hasNoMiddleName by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var house by remember { mutableStateOf("") }
    var building by remember { mutableStateOf("") }
    var flat by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val openExitDialog = remember { mutableStateOf(false) }
    val isFormFilled by remember(
        name, surname, patronymic, city, street, house,
        building, flat, floor, phone
    ) {
        mutableStateOf(
            name.isNotBlank() ||
                    surname.isNotBlank() ||
                    patronymic.isNotBlank() ||
                    city.isNotBlank() ||
                    street.isNotBlank() ||
                    house.isNotBlank() ||
                    building.isNotBlank() ||
                    flat.isNotBlank() ||
                    floor.isNotBlank() ||
                    phone.isNotBlank()
        )
    }

    BackHandler(enabled = true) {
        if (isFormFilled) {
            openExitDialog.value = true
        } else {
            navController.popBackStack()
        }
    }



    LaunchedEffect(state) {
        state.collectLatest { state ->
            when (state) {
                is RecipientAddUIState.Success -> {
                    Toast.makeText(context, context.getString(R.string.recipient_added), Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is RecipientAddUIState.Error -> {
                    Toast
                        .makeText(context, context.getString(state.errorResId), Toast.LENGTH_LONG)
                        .show()
                }
                is RecipientAddUIState.RequiredFieldsAreEmpty -> {
                    Toast.makeText(context, context.getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
                }
                is RecipientAddUIState.Unauthorized -> {
                    Toast.makeText(context, "Неавторизованный доступ", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route) {
                    }
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
                .padding(16.dp)
        ) {

            Text(
                text = "Введите данные о новом получателе",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = hasNoMiddleName,
                    onCheckedChange = { hasNoMiddleName = it }
                )
                Text("Нет отчества")
            }

            OutlinedTextField(
                value = patronymic,
                onValueChange = { patronymic = it },
                label = { Text("Отчество") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                enabled = !hasNoMiddleName,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Город") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = street,
                onValueChange = { street = it },
                label = { Text("Улица") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = house,
                    onValueChange = { house = it },
                    label = { Text("Дом", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )
                OutlinedTextField(
                    value = building,
                    onValueChange = { building = it },
                    label = { Text("Корпус", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )
                OutlinedTextField(
                    value = flat,
                    onValueChange = { flat = it },
                    label = { Text("Квартира", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )
                OutlinedTextField(
                    value = floor,
                    onValueChange = { floor = it },
                    label = { Text("Этаж", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val recipient = Recipient(
                        name = name,
                        surname = surname,
                        patronymic = patronymic.ifBlank { null },
                        phone = phone,
                        city = city,
                        street = street,
                        house = house.toInt(),
                        building = building.ifBlank { null },
                        flat = flat.toInt(),
                        floor = floor.toInt()
                    )
                    coroutineScope.launch {
                        recipientViewModel.addRecipient(recipient)
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("Сохранить", color = Color.White)
            }
            ConfirmExitDialog(
                visible = openExitDialog.value,
                onConfirm = {
                    openExitDialog.value = false
                    navController.popBackStack()
                },
                onDismiss = { openExitDialog.value = false }
            )
        }
    }
}

@Composable
fun ConfirmExitDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String = "Подтверждение",
    message: String = "Вы действительно хотите выйти без сохранения?"
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = onConfirm) { Text("Да") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Отмена") }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }
}
