package com.sudarshanhs.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudarshanhs.chatapp.ui.screen.chat.ChatScreen
import com.sudarshanhs.chatapp.ui.screen.home.HomeScreen
import com.sudarshanhs.chatapp.ui.screen.login.LoginScreen
import com.sudarshanhs.chatapp.ui.screen.splash.SplashScreen
import com.sudarshanhs.chatapp.ui.theme.ChatAppTheme
import com.sudarshanhs.chatapp.ui.utility.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ChatApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Chat.route + "/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ChatScreen(navController, userId)
        }
    }
}

