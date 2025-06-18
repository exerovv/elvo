package com.example.elvo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.elvo.ui.viewmodels.order.OrderHistoryUIState
import com.example.elvo.ui.viewmodels.order.OrderViewModel
import com.example.elvo.ui.viewmodels.order.SingleOrderUIState
import com.example.elvo.utils.formatDateForUi
import kotlinx.coroutines.launch

@Composable
fun OrderingDetailScreen(
    navController: NavController,
    orderingId: Int,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val orderState by viewModel.singleOrderState.collectAsState()
    val historyState by viewModel.historyState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(orderingId) {
        viewModel.fetchSingleOrder(orderingId)
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
            when (val state = orderState) {
                is SingleOrderUIState.Success -> {
                    val order = state.data.order
                    Text(order.orderName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(16.dp))

                    OrderField(label = "Статус", value = order.currentStatus)
                    OrderField(label = "Вес товара", value = "${order.weight} кг")
                    OrderField(label = "Дата создания", value = formatDateForUi(order.createdAt))
                    OrderField(label = "Ссылка", value = order.link)
                    OrderField(label = "Описание(RU)", value = order.ruDescription)
                    OrderField(label = "Описание(CH)", value = order.chDescription)
                    OrderField(label = "Итоговая сумма", value = order.totalPrice.toString())

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                isDialogOpen = true
                                coroutineScope.launch {
                                    viewModel.fetchOrderHistory(orderingId)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text("Отследить", color = Color.White)
                        }
                    }


                }

                is SingleOrderUIState.Error -> {
                    Text("Ошибка")
                }

                is SingleOrderUIState.Unauthorized -> {
                    Text("Неавторизован")
                }

                else -> {
                    Text("Загрузка...")
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                TextButton(onClick = { isDialogOpen = false }) {
                    Text("Закрыть")
                }
            },
            title = { Text("История заказа") },
            text = {
                when (val history = historyState) {
                    is OrderHistoryUIState.Success -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            history.data.forEach { status ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(status.icon),
                                        contentDescription = "icon",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFF4DED3))
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = status.name,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                            color = Color.Black
                                        )
                                        Text(
                                            text = formatDateForUi(status.createdAt),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is OrderHistoryUIState.Error -> Text("Ошибка при получении истории")
                    is OrderHistoryUIState.Unauthorized -> Text("Неавторизован")
                    else -> Text("Загрузка истории...")
                }
            }
        )
    }
}


@Composable
fun OrderField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}
