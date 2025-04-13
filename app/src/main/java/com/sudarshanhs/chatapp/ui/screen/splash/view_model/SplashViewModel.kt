package com.sudarshanhs.chatapp.ui.screen.splash.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sudarshanhs.chatapp.core.utility.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val sharedPrefsHelper: SharedPrefsHelper) : ViewModel(){


    val isLoggedIn = mutableStateOf(false)

    init {
        isLoggedIn.value = sharedPrefsHelper.getLoginState()
    }

}