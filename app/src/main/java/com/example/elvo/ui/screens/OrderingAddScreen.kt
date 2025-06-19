package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.R
import com.example.elvo.domain.model.order.Order
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors
import com.example.elvo.ui.viewmodels.order.OrderAddUIState
import com.example.elvo.ui.viewmodels.order.OrderViewModel
import com.example.elvo.ui.viewmodels.recipient.RecipientListUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import kotlinx.coroutines.launch

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
                navController.navigate(Screen.Login.route) {
                }
            }
            else -> {}
        }
    }

    LaunchedEffect(addOrderState) {
        when (addOrderState) {
            is OrderAddUIState.Success -> {
                Toast.makeText(context,
                    context.getString(R.string.added_successfully), Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is OrderAddUIState.RequiredFieldsAreEmpty -> Toast.makeText(context, context.getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
            is OrderAddUIState.IncorrectLink -> Toast.makeText(context, context.getString(R.string.incorrect_link), Toast.LENGTH_SHORT).show()
            is OrderAddUIState.Unauthorized -> {}
            is OrderAddUIState.Error -> Toast.makeText(context, context.getString((addOrderState as OrderAddUIState.Error).errorResId), Toast.LENGTH_SHORT).show()
            null -> {}
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
                label = { Text(stringResource(R.string.track_number)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = orderName,
                onValueChange = { orderName = it },
                label = { Text(stringResource(R.string.order_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descriptionRu,
                onValueChange = { descriptionRu = it },
                label = { Text(stringResource(R.string.product_description_ru)) },
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
                label = { Text(stringResource(R.string.product_description_ch)) },
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
                label = { Text(stringResource(R.string.poizon)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(stringResource(R.string.price)) },
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
                        Toast.makeText(context,
                            context.getString(R.string.select_recipient), Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(stringResource(R.string.add_title), color = Color.White)
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
            value = selectedRecipient?.name ?: stringResource(R.string.select_recipient),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.recipient)) },
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
