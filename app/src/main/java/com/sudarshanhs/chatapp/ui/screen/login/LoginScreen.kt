package com.sudarshanhs.chatapp.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sudarshanhs.chatapp.R
import com.sudarshanhs.chatapp.ui.screen.login.view_model.LoginViewModel
import com.sudarshanhs.chatapp.ui.theme.Purple40
import com.sudarshanhs.chatapp.ui.utility.Screen


@Composable
fun LoginScreen(navController: NavController) {

    val viewModel: LoginViewModel = hiltViewModel()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val errorMsg = stringResource(id = R.string.under_development)
    val fieldErrorMsg = stringResource(id = R.string.fields_cannot_be_empty)

    val color =  Color(0xFF000080)


    if (viewModel.isLoginSuccess.value) {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.startDestinationRoute!!) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    if (viewModel.errorMsg.value.isNotEmpty()) {
        Toast.makeText(context, ""+viewModel.errorMsg.value , Toast.LENGTH_SHORT).show()
        viewModel.errorMsg.value = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.chat_logo),
            tint = Color(0xFFB38E3B),
            modifier = Modifier
                .size(100.dp)
                .background(color, shape = CircleShape)
                .padding(24.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            border = BorderStroke(1.dp, color),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(stringResource(id = R.string.enter_email)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text(stringResource(id = R.string.enter_password)) },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = stringResource(id = R.string.toggle_password)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (email.value.isBlank() || password.value.isBlank()) {
                            Toast.makeText(
                                context,
                                fieldErrorMsg,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.loginWithEmail(
                                email = email.value,
                                password = password.value
                            )
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.login), color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))


            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_account))
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Purple40
                    )
                ) {
                    append(stringResource(id = R.string.sign_up))
                }
            },
            modifier = Modifier.clickable {
                Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
