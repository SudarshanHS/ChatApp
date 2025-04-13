package com.sudarshanhs.chatapp.ui.screen.splash


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sudarshanhs.chatapp.R
import com.sudarshanhs.chatapp.ui.screen.splash.view_model.SplashViewModel
import com.sudarshanhs.chatapp.ui.utility.Screen


@Composable
fun SplashScreen(navController: NavController) {

    val viewModel: SplashViewModel = hiltViewModel()

    SplashScreenUI(navController, viewModel)

}


@Composable
fun SplashScreenUI(navController: NavController, viewModel: SplashViewModel) {


    val buttonText = if (viewModel.isLoggedIn.value) {
        stringResource(id = R.string.proceed)
    } else {
        stringResource(id = R.string.login_using_user_name)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Text(
                text = stringResource(id = R.string.app_name),
                color = Color(0xFF000080),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (viewModel.isLoggedIn.value) {
                        navController.navigate(Screen.Home.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF000080)
                ),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = buttonText,
                    color = Color.White
                )
            }
        }
    }
}
