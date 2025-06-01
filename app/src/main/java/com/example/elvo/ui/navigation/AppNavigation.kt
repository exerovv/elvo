package com.example.elvo.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.example.elvo.ui.theme.PlusJakartaSans
import com.example.elvo.R
import com.example.elvo.ui.auth.screens.LoginScreen
import com.example.myapplication.HomeScreen
import com.example.myapplication.OrderScreen
import com.example.myapplication.ProfileScreen
import com.example.myapplication.RecipientDetailScreen
import com.example.myapplication.RecipientListScreen


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
    object RecipientList : Screen("recipient_list")
    object RecipientDetail : Screen("recipient_detail")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val bottomBarScreens = listOf("home", "orders", "profile")

    val showBottomBar = currentDestination in bottomBarScreens
    val showTopBar = currentDestination != "login"

    Scaffold(
        topBar = {
            if (showTopBar) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF7FAFC),
                        titleContentColor = Color.Black
                    ),
                    title = {
                        Text(
                            when (currentDestination) {
                                "register" -> "Регистрация"
                                "profile" -> "Профиль"
                                "orders" -> "Заказы"
                                "home" -> "Главная"
                                "recipient_list" -> "Список получателей"
                                "recipient_detail" -> "Получатель"
                                else -> ""
                            },

                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontFamily = PlusJakartaSans
                            )
                        )
                    },
                    navigationIcon = {
                        if (currentDestination !in listOf("home", "profile")) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFFF7FAFC)
                )
                {
                    NavigationBarItem(
                        selected = currentDestination == "home",
                        onClick = {
                            if (currentDestination != "home") {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                            painter = painterResource(R.drawable.ic_home),
                            contentDescription = "Главная"
                        ) },
                        label = { Text("Главная") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0B57D0),
                            selectedTextColor = Color(0xFF0B57D0),
                            indicatorColor = Color(0x330B57D0),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )

                    )
                    NavigationBarItem(
                        selected = currentDestination == "orders",
                        onClick = {
                            if (currentDestination != "orders") {
                                navController.navigate("orders") {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_order),
                                contentDescription = "Заказы"
                            ) },
                        label = { Text("Заказы") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0B57D0),
                            selectedTextColor = Color(0xFF0B57D0),
                            indicatorColor = Color(0x330B57D0),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                    NavigationBarItem(
                        selected = currentDestination == "profile",
                        onClick = {
                            if (currentDestination != "profile") {
                                navController.navigate("profile") {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Профиль") },
                        label = { Text("Профиль") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0B57D0),
                            selectedTextColor = Color(0xFF0B57D0),
                            indicatorColor = Color(0x330B57D0),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { navController.navigate("home") },
                    onRegisterClick = { navController.navigate("register") }
                )
            }
            composable("register") {
                com.example.elvo.ui.auth.screens.RegisterScreen()
            }
            composable("home") { HomeScreen() }
            composable("orders") { OrderScreen() }
            composable("profile") { ProfileScreen(navController) }
            composable("recipient_list") { RecipientListScreen(navController) }
            composable("recipient_detail") { RecipientDetailScreen(navController) }
        }
    }
}
