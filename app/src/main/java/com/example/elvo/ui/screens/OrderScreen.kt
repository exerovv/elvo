package com.example.elvo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.elvo.ui.navigation.Screen
import com.example.elvo.ui.viewmodels.order.OrderListUIState
import com.example.elvo.ui.viewmodels.order.OrderViewModel
import com.example.elvo.utils.PaymentStatus
import com.example.elvo.utils.toUiString


@Composable
fun OrderScreen(navController: NavController, viewModel: OrderViewModel = hiltViewModel()) {
    val tabs = listOf("Созданные", "В пути", "Завершенные")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabStatuses = listOf("created", "in_transit", "completed")

    val listState by viewModel.listState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrders()
    }

    val filteredOrders = when (listState) {
        is OrderListUIState.Success -> {
            (listState as OrderListUIState.Success).data
                .filter { it.globalStatus == tabStatuses[selectedTabIndex] }
        }
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC))
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xFFF7FAFC),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0xFF0B57D0)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(0xFF0E0D0D) else Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }

        when (listState) {
            is OrderListUIState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Произошла ошибка", color = Color.Red)
                }
            }
            is OrderListUIState.Unauthorized -> {
                navController.navigate(Screen.Login.route) {
                }
            }
            else -> {
                if (filteredOrders.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Нет заказов", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(filteredOrders) { order ->
                            OrderCard(
                                orderName = order.orderName,
                                currentStatus = order.currentStatus,
                                paymentStatus = PaymentStatus.valueOf(order.paymentStatus).toUiString(),
                            ) {
                                navController.navigate("order_detail/${order.orderingId}")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(orderName: String, currentStatus: String, paymentStatus: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = orderName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF212121),
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = paymentStatus,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF808080),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentStatus,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF808080),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}