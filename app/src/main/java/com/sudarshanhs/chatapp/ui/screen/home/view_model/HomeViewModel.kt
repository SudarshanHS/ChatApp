package com.sudarshanhs.chatapp.ui.screen.home.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudarshanhs.chatapp.core.AppUser
import com.sudarshanhs.chatapp.core.utility.AppUtils
import com.sudarshanhs.chatapp.core.utility.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper
) : ViewModel() {

    private val _userList = mutableStateListOf<AppUser>()
    val userList: List<AppUser> get() = _userList


    init {

        val user = FirebaseAuth.getInstance().currentUser

        fetchUsers()
    }

    /**
     * Fetches the list of users from the Firestore database.
     * On success, updates the user list.
     * On failure, logs the error message.
     */
    private fun fetchUsers() {
        Firebase.firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                _userList.clear()
                _userList.addAll(getListOfUsers(result))
            }
            .addOnFailureListener { e ->
                Log.e("error", "Failed to load users: ${e.message}")
            }
    }


    /**
     * Processes the Firestore query result to create a list of users.
     * Removes the currently logged-in user from the list.
     *
     * @param result The Firestore query result containing user documents.
     * @return A filtered list of users excluding the logged-in user.
     */
    private fun getListOfUsers(result: QuerySnapshot): List<AppUser> {
        val userList = mutableListOf<AppUser>()
        if (result.size() == 0) {
            userList.addAll(AppUtils.getDefaultUsers())
        } else {
            for (doc in result) {
                val user = doc.toObject(AppUser::class.java)
                userList.add(user)
            }
        }
        // remove logged in user
        val myUserId = sharedPrefsHelper.getUserId() ?: ""
        return userList.filter { it.uid != myUserId }

    }

    /**
     * Logs out the user by clearing all shared preferences.
     */
    fun logout() {
        sharedPrefsHelper.clearPrefs()
    }
}
