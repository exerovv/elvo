package com.example.elvo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.domain.model.recipient.RecipientShort
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.viewmodels.recipient.RecipientListUIState
import com.example.elvo.ui.viewmodels.recipient.RecipientViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.layout.Row as Row1


@Composable
fun RecipientListScreen(navController: NavController, recipientViewModel: RecipientViewModel = hiltViewModel()) {
    val state = recipientViewModel.listState
    val context = LocalContext.current
    val recipients = remember { mutableStateOf<List<RecipientShort>>(emptyList()) }

    LaunchedEffect(Unit) {
        recipientViewModel.fetchRecipients()
    }

    LaunchedEffect(Unit) {
        state.collectLatest { state ->
            when (state) {
                is RecipientListUIState.Default -> {}

                is RecipientListUIState.Success -> {
                    recipients.value = state.data
                }

                is RecipientListUIState.Error -> {
                    Toast
                        .makeText(context, context.getString(state.errorResId), Toast.LENGTH_LONG)
                        .show()
                }

                is RecipientListUIState.Unauthorized -> {
                    Toast
                        .makeText(context, "Вы не авторизованы", Toast.LENGTH_LONG)
                        .show()
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            recipients.value.forEach { recipient ->
                Row1(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable {
                            navController.navigate(Screen.RecipientDetail.createRoute(recipient.recipientId))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(recipient.name, style = MaterialTheme.typography.bodyLarge)
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row1(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { navController.navigate("recipient_add")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Добавить получателя", color = Color.White)
                }
            }
        }
    }
}
