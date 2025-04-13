package com.sudarshanhs.chatapp.core.utility

import com.sudarshanhs.chatapp.core.AppUser

object AppUtils {

    fun getDefaultUsers(): List<AppUser> {
        return listOf(
            AppUser(uid = "5uwfzRIYsDM4HPGlsUuWVSIOHwF2", name = "Emily Johnson", email = "emily.johnson@chat.com"),
            AppUser(uid = "7uzeuCYwD1THE0TOMKceQyrHENG3", name = "James Anderson", email = "james.anderson@chat.com"),
        )
    }

    fun getUsernameByUid(uid: String): String {
        val users = getDefaultUsers()
        return users.find { it.uid == uid }?.name ?: "Unknown User"
    }

}