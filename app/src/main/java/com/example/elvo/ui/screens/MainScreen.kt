package com.example.elvo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun MainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF5F5))
            .padding(24.dp)
    ) {
        Text(
            text = "Популярные товары",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F),
                letterSpacing = 0.5.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Тут будут товары", color = Color.Black)

            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "О компании",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Мы — ELVO. Быстрая доставка и надёжность в каждой покупке.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
    }
}
