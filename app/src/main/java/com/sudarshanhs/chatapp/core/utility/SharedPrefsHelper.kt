package com.sudarshanhs.chatapp.core.utility

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_LOGGED_IN = "key_logged_in"
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun saveLoginState() {
        sharedPreferences.edit().putBoolean(KEY_LOGGED_IN, true).apply()
    }

    fun getLoginState():Boolean {
        return  sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
    }


    fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }

}