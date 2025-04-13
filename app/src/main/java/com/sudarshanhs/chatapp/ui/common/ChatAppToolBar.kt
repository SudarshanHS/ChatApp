package com.sudarshanhs.chatapp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sudarshanhs.chatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppToolBar1(title: String = stringResource(id = R.string.app_name), modifier: Modifier) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF000080)
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppToolBar(
    title: String = stringResource(id = R.string.app_name),
    modifier: Modifier = Modifier,
    showLogoutButton : Boolean = false,
    onLogoutClick: () -> Unit // Pass logout callback
) {
    TopAppBar(
        title = {
            Text(text = title, color = Color.White)
        },
        modifier = modifier,
        actions = {
            if(showLogoutButton) {
                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF000080)
        )
    )
}
