package com.example.elvo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.*

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem("Main", "Главная", Icons.Default.Home),
        BottomNavItem("Orders", "Заказы", Icons.Default.ShoppingCart),
        BottomNavItem("Profile", "Профиль", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFFD32F2F),
                tonalElevation = 8.dp
            ) {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selected = currentDestination == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFD32F2F),
                            selectedTextColor = Color(0xFFD32F2F),
                            indicatorColor = Color(0xFFFFEBEE),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        },
        containerColor = Color(0xFFFDF5F5)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Main") { MainScreen() }
            composable("Orders") { OrdersScreen() }
            composable("Profile") { ProfileScreen() }
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
