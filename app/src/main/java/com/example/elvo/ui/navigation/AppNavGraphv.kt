package com.example.elvo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.elvo.ui.screens.*


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object HomeRoot : Screen("home_root")
    object Main : Screen("main")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.HomeRoot.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(Screen.HomeRoot.route) {
            HomeScreen()
        }
    }
}
