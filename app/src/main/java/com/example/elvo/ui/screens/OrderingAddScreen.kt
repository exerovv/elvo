package com.example.elvo.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.order.OrderAddUIState
import com.example.elvo.ui.viewmodels.order.OrderViewModel
import com.example.elvo.ui.viewmodels.recipient.RecipientListUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderingAddScreen(navController: NavController,
                      recipientViewModel: RecipientViewModel = hiltViewModel(),
                      orderViewModel: OrderViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val listState by recipientViewModel.listState.collectAsState()
    val addOrderState by orderViewModel.addState.collectAsState(initial = null)

    var recipientList by remember { mutableStateOf<List<RecipientShort>>(emptyList()) }

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    var trackNumber by remember { mutableStateOf("") }
    var orderName by remember { mutableStateOf("") }
    var descriptionRu by remember { mutableStateOf("") }
    var descriptionCn by remember { mutableStateOf("") }
    var poizonLink by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    LaunchedEffect(listState) {
        when (listState) {
            is RecipientListUIState.Success -> {
                recipientList = (listState as RecipientListUIState.Success).data
            }
            is RecipientListUIState.Error -> {
                Toast.makeText(context,
                    context.getString((listState as RecipientListUIState.Error).errorResId),
                    Toast.LENGTH_LONG
                ).show()
            }
            is RecipientListUIState.Unauthorized -> {
                Toast.makeText(context, "Неавторизованный доступ", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(addOrderState) {
        when (addOrderState) {
            is OrderAddUIState.Success -> {
                Toast.makeText(context, "Заказ успешно добавлен", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is OrderAddUIState.RequiredFieldsAreEmpty -> Toast.makeText(context, "Пожалуйста, заполните все обязательные поля", Toast.LENGTH_SHORT).show()
            is OrderAddUIState.IncorrectLink -> Toast.makeText(context, "Некорректная ссылка", Toast.LENGTH_SHORT).show()
            is OrderAddUIState.Unauthorized -> Toast.makeText(context, "Вы не авторизованы", Toast.LENGTH_SHORT).show()
            is OrderAddUIState.Error -> Toast.makeText(context, context.getString((addOrderState as OrderAddUIState.Error).errorResId), Toast.LENGTH_SHORT).show()
            null -> Toast.makeText(context, "Нет состояния", Toast.LENGTH_SHORT).show()
        }
    }

    var selectedRecipient by remember { mutableStateOf<RecipientShort?>(null) }

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
                value = trackNumber,
                onValueChange = { trackNumber = it },
                label = { Text("Трек номер") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = orderName,
                onValueChange = { orderName = it },
                label = { Text("Название заказа") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descriptionRu,
                onValueChange = { descriptionRu = it },
                label = { Text("Описание товара (RU)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descriptionCn,
                onValueChange = { descriptionCn = it },
                label = { Text("Описание товара (CH)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = poizonLink,
                onValueChange = { poizonLink = it },
                label = { Text("Ссылка на Poizon") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Цена") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            RecipientDropdownMenu(
                recipients = recipientList,
                selectedRecipient = selectedRecipient,
                onRecipientSelected = { selectedRecipient = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val priceValue = price.toDoubleOrNull()
                    if (selectedRecipient != null) {
                        val order = Order(
                            trackNumber = trackNumber,
                            orderName = orderName,
                            ruDescription = descriptionRu,
                            chDescription = descriptionCn,
                            link = poizonLink,
                            itemPrice = priceValue,
                            recipientId = selectedRecipient!!.recipientId
                        )

                        coroutineScope.launch {
                            orderViewModel.addOrder(order)
                        }
                    } else {
                        Toast.makeText(context, "Выберите получателя", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("Добавить", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipientDropdownMenu(
    recipients: List<RecipientShort>,
    selectedRecipient: RecipientShort?,
    onRecipientSelected: (RecipientShort) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedRecipient?.name ?: "Выберите получателя",
            onValueChange = {},
            readOnly = true,
            label = { Text("Получатель") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = textFieldColors
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

        ) {
            recipients.forEach { recipient ->
                DropdownMenuItem(
                    text = { Text(recipient.name) },
                    onClick = {
                        onRecipientSelected(recipient)
                        expanded = false
                    }
                )
            }
        }
    }
}
