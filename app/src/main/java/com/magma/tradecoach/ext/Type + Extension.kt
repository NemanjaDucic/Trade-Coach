package com.magma.tradecoach.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Date.formatted: String
    get() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(this)
    }
fun String.splitTime(): String {
    return try {
        val split = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        split[1]
    } catch (e: Exception) {
        " "
    }
}