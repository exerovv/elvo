package com.example.elvo.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.elvo.ui.theme.PlusJakartaSans
import com.example.elvo.R
import com.example.elvo.ui.auth.screens.LoginScreen
import com.example.elvo.ui.auth.screens.RegisterScreen
import com.example.elvo.ui.screens.ConfirmExitDialog
import com.example.elvo.ui.screens.FAQScreen
import com.example.elvo.ui.screens.OrderingAddScreen
import com.example.elvo.ui.screens.OrderingDetailScreen
import com.example.elvo.ui.screens.RecipientAddScreen
import com.example.elvo.ui.screens.HomeScreen
import com.example.elvo.ui.screens.OrderScreen
import com.example.elvo.ui.screens.ProfileScreen
import com.example.elvo.ui.screens.RecipientDetailScreen
import com.example.elvo.ui.screens.RecipientEditScreen
import com.example.elvo.ui.screens.RecipientListScreen


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Orders : Screen("orders")
    object OrderAdd : Screen("order_add")
    object Profile : Screen("profile")
    object FAQ: Screen("faq")
    object RecipientList : Screen("recipient_list")
    object RecipientEdit : Screen("recipient_edit/{id}")
    object RecipientDetail : Screen("recipient_detail/{id}") {
        fun createRoute(id: Int) = "recipient_detail/$id"
    }
    object RecipientAdd : Screen("recipient_add")
    object OrderDetail : Screen("order_detail/{orderingId}") {
        fun createRoute(orderingId: Int) = "order_detail/$orderingId"
    }

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

    val showExitDialog = remember { mutableStateOf(false) }

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
                                Screen.Register.route -> stringResource(R.string.signup)
                                Screen.Profile.route -> stringResource(R.string.Profile)
                                Screen.Orders.route -> stringResource(R.string.orders)
                                Screen.Home.route  -> stringResource(R.string.main)
                                Screen.RecipientList.route -> stringResource(R.string.list_recipients)
                                Screen.RecipientDetail.route -> stringResource(R.string.recipient)
                                Screen.RecipientAdd.route -> stringResource(R.string.new_recipient)
                                Screen.RecipientEdit.route -> stringResource(R.string.Editing)
                                Screen.OrderDetail.route -> stringResource(R.string.order_Information)
                                Screen.OrderAdd.route -> stringResource(R.string.add_ordering)
                                Screen.FAQ.route -> stringResource(R.string.FAQ)
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
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(
                                    R.string.Back
                                )
                                )
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
                            contentDescription = stringResource(R.string.main)
                        ) },
                        label = { Text(stringResource(R.string.main)) },
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
                                contentDescription = stringResource(R.string.orders)
                            ) },
                        label = { Text(stringResource(R.string.orders)) },
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
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_profile),
                                contentDescription = stringResource(R.string.Profile)
                            ) },
                        label = { Text(stringResource(R.string.Profile)) },
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
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("profile") },
                    onRegisterClick = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onLoginSuccess = {
                        navController.navigate("home"){
                            popUpTo("register") { inclusive = true }
                        }
                    }

                )
            }

            composable("home") { HomeScreen() }
            composable("orders") { OrderScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("recipient_list") { RecipientListScreen(navController) }
            composable(
                route = Screen.RecipientDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                RecipientDetailScreen(navController = navController,recipientId = id)
            }
            composable(
                route = "recipient_edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                RecipientEditScreen(navController = navController, recipientId = id)
            }
            composable("recipient_add") { RecipientAddScreen(navController) }
            composable(
                route = Screen.OrderDetail.route,
                arguments = listOf(navArgument("orderingId") { type = NavType.IntType })
            ) { backStackEntry ->
                val orderingId = backStackEntry.arguments?.getInt("orderingId") ?: return@composable
                OrderingDetailScreen(navController = navController, orderingId = orderingId)
            }
            composable("order_add") { OrderingAddScreen(navController) }
            composable("faq") { FAQScreen() }
        }
    }

    ConfirmExitDialog(
        visible = showExitDialog.value,
        onConfirm = {
            showExitDialog.value = false
            navController.popBackStack()
        },
        onDismiss = { showExitDialog.value = false }
    )
}
