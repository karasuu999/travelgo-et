package com.example.travelgo.ui.navegation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    // Si después quieres detalle de paquetes:
    // object PackageDetail : Screen("packageDetail/{id}") {
    //     fun createRoute(id: Int) = "packageDetail/$id"
    // }
}