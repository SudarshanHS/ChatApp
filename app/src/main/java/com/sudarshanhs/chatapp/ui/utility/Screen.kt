package com.sudarshanhs.chatapp.ui.utility

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Chat : Screen("chat")
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
}