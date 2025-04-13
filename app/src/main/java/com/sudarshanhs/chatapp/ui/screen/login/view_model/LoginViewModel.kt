package com.sudarshanhs.chatapp.ui.screen.login.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sudarshanhs.chatapp.core.utility.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedPrefsHelper: SharedPrefsHelper) : ViewModel() {

    val isLoginSuccess = mutableStateOf(false)
    val errorMsg = mutableStateOf("")

    /**
     * Function to log in a user with email and password.
     * On success, updates login state and saves user ID in shared preferences.
     * On failure, updates the error message state.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    fun loginWithEmail(
        email: String,
        password: String,
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoginSuccess.value = true
                sharedPrefsHelper.saveUserId(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                sharedPrefsHelper.saveLoginState()
            }
            .addOnFailureListener {
                isLoginSuccess.value = false
                errorMsg.value = it.message ?: "Login Failed"
            }
    }
}