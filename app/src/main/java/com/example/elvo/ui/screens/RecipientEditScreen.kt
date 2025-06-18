package com.example.elvo.ui.screens

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.domain.model.recipient.Recipient
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.recipient.RecipientUpdateUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import com.example.elvo.ui.viewmodels.recipient.SingleRecipientUIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RecipientEditScreen(
    navController: NavController,
    recipientId: Int,
    recipientViewModel: RecipientViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = recipientViewModel.updateState
    val singleState = recipientViewModel.singleRecipientState


    val coroutineScope = rememberCoroutineScope()

    var recipientLoaded by remember { mutableStateOf(false) }
    var oldRecipient by remember { mutableStateOf<Recipient?>(null) }

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

    LaunchedEffect(recipientId) {
        recipientViewModel.fetchSingleRecipient(recipientId)
    }

    LaunchedEffect(singleState) {
        singleState.collectLatest { state ->
            if (state is SingleRecipientUIState.Success && !recipientLoaded) {
                val recipient = state.data
                name = recipient.name
                surname = recipient.surname
                patronymic = recipient.patronymic
                hasNoMiddleName = recipient.patronymic.isBlank()
                city = recipient.city
                street = recipient.street
                house = recipient.house.toString()
                building = recipient.building ?: ""
                flat = recipient.flat.toString()
                floor = recipient.floor.toString()
                phone = recipient.phone
                oldRecipient = Recipient(
                    name = name,
                    surname = surname,
                    patronymic = patronymic,
                    phone = phone,
                    city = city,
                    street = street,
                    house = house.toInt(),
                    building = building,
                    flat = flat.toInt(),
                    floor = floor.toInt()
                )
                recipientLoaded = true
            }
        }
    }

    LaunchedEffect(state) {
        state.collectLatest {
            when (it) {
                is RecipientUpdateUIState.Success -> {
                    Toast.makeText(context, "Получатель обновлен", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is RecipientUpdateUIState.Error -> {
                    Toast.makeText(context, context.getString(it.errorResId), Toast.LENGTH_LONG).show()
                }
                is RecipientUpdateUIState.RequiredFieldsAreEmpty -> {
                    Toast.makeText(context, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                }
                is RecipientUpdateUIState.Unauthorized -> {
                    Toast.makeText(context, "Неавторизованный доступ", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route) {                  }
                }
                else -> {}
            }
        }
    }

    RecipientEditForm(
        name = name,
        onNameChange = { name = it },
        surname = surname,
        onSurnameChange = { surname = it },
        patronymic = patronymic,
        onPatronymicChange = { patronymic = it },
        hasNoMiddleName = hasNoMiddleName,
        onHasNoMiddleNameChange = { hasNoMiddleName = it },
        city = city,
        onCityChange = { city = it },
        street = street,
        onStreetChange = { street = it },
        house = house,
        onHouseChange = { house = it },
        building = building,
        onBuildingChange = { building = it },
        flat = flat,
        onFlatChange = { flat = it },
        floor = floor,
        onFloorChange = { floor = it },
        phone = phone,
        onPhoneChange = { phone = it },
        onSaveClick = {
            val newRecipient = Recipient(
                name = name,
                surname = surname,
                patronymic = if (hasNoMiddleName) null else patronymic,
                city = city,
                street = street,
                house = house.toInt(),
                building = building.ifBlank { null },
                flat = flat.toIntOrNull(),
                floor = floor.toIntOrNull(),
                phone = phone
            )
            coroutineScope.launch {
                oldRecipient?.let {
                    recipientViewModel.updateRecipient(
                        id = recipientId,
                        newRecipient = newRecipient,
                        oldRecipient = it
                    )
                }
            }
        }
    )
}




@Composable
fun RecipientEditForm(
    name: String,
    onNameChange: (String) -> Unit,
    surname: String,
    onSurnameChange: (String) -> Unit,
    patronymic: String,
    onPatronymicChange: (String) -> Unit,
    hasNoMiddleName: Boolean,
    onHasNoMiddleNameChange: (Boolean) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    street: String,
    onStreetChange: (String) -> Unit,
    house: String,
    onHouseChange: (String) -> Unit,
    building: String,
    onBuildingChange: (String) -> Unit,
    flat: String,
    onFlatChange: (String) -> Unit,
    floor: String,
    onFloorChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    ) {
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = surname,
                onValueChange = onSurnameChange,
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = patronymic,
                onValueChange = onPatronymicChange,
                label = { Text("Отчество") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !hasNoMiddleName,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = hasNoMiddleName,
                    onCheckedChange = onHasNoMiddleNameChange
                )
                Text("Нет отчества")
            }


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = city,
                onValueChange = onCityChange,
                label = { Text("Город") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = street,
                onValueChange = onStreetChange,
                label = { Text("Улица") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){OutlinedTextField(
                value = house,
                onValueChange = onHouseChange,
                label = { Text("Дом", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
            )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = building,
                    onValueChange = onBuildingChange,
                    label = { Text("Корпус", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = flat,
                    onValueChange = onFlatChange,
                    label = { Text("Квартира", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = floor,
                    onValueChange = onFloorChange,
                    label = { Text("Этаж", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("Сохранить")
            }
        }

    }
}
