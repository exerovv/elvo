package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elvo.ui.theme.AppTextFieldDefaults.textFieldColors


@Composable
fun RecipientDetailScreen(navController: NavController) {
    var isEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("Алексей Смирнов") }
    var phone by remember { mutableStateOf("+7(916)123-45-67") }
    var address by remember { mutableStateOf("Москва, ул. Ленина, дом 6, корпус 2") }

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
                        // TODO: Сохранить в БД при необходимости
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
