package com.example.travelgo.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelgo.ui.screens.HomeScreen
import com.example.travelgo.ui.screens.LoginScreen
import com.example.travelgo.ui.screens.ProfileScreen
import com.example.travelgo.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Profile.route) {
            // ProfileScreen no necesita navController (si lo quieres, se lo agregamos después)
            ProfileScreen()
        }
    }
}
