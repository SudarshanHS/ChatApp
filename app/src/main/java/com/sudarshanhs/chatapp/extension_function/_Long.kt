package com.sudarshanhs.chatapp.extension_function

fun Long.formatToTime(): String {
    val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}