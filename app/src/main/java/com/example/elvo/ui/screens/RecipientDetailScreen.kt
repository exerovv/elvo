package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RecipientDetailScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7FAFC)
    )
    {
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color(0xFFF7FAFC))
    ) {
        Text("Данные", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        RecipientField(label = "Имя", value = "Алексей Смирнов")
        RecipientField(label = "Номер телефона", value = "+7(916)123-45-67")
        RecipientField(label = "Адрес", value = "Москва, ул. Ленина, дом 6, корпус 2")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* TODO: Обработка редактирования */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("Редактировать", color = Color.White)
        }
    }}

}

@Composable
fun RecipientField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyLarge)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
