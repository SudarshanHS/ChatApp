package com.sudarshanhs.chatapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sudarshanhs.chatapp.ui.common.ChatAppToolBar
import com.sudarshanhs.chatapp.ui.screen.home.view_model.HomeViewModel
import com.sudarshanhs.chatapp.ui.utility.Screen


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()


    Column {
        ChatAppToolBar(modifier = Modifier.fillMaxWidth(), showLogoutButton = true){
            // Handle logout action
            viewModel.logout()
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true } // Clears the entire back stack
            }
        }

        UserListScreen(viewModel = viewModel) { userId ->
            // Handle user click
            navController.navigate(Screen.Chat.route + "/$userId"){
                popUpTo(Screen.Home.route) {
                    inclusive = false // Keep HomeScreen in the stack
                }
                launchSingleTop = true
            }
        }
    }

}

/**
 * UserListScreen displays a list of users in a scrollable column.
 * Each user is represented by a card that can be clicked to navigate to the chat screen.
 *
 * @param viewModel The HomeViewModel instance providing user data.
 * @param onUserClick Callback function to handle user click events.
 */
@Composable
fun UserListScreen(viewModel: HomeViewModel, onUserClick: (String) -> Unit) {
    val users = viewModel.userList

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        items(users) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { onUserClick(user.uid) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    // Avatar or Placeholder
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFFBBDEFB),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.firstOrNull()?.uppercase() ?: "?",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Name & optional status
                    Column {
                        Text(
                            text = user.name,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "Online", // replace with actual status if available
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    }
}
