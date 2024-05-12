package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ConsecutiveDayChecker(private val context: Context) {

    private var preferences = PrefSingleton.instance

    @SuppressLint("SimpleDateFormat")
    fun onUserLogin(userId: String) {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val today = dateFormat.format(date)
        val lastLoginDay = getLastLoginDate()

        val yesterday = getYesterdayDate(dateFormat, date)

        if (lastLoginDay == null) {
            updateLastLoginDate(today)
            incrementDays(userId)
        } else {
            if (lastLoginDay == today) {
                Utils.didShowLogin = true
                // User logged in the same day, do nothing
            } else if (lastLoginDay == yesterday) {
                // User logged in consecutive days, add 1
                Utils.didShowLogin = false
                updateLastLoginDate(today)
                incrementDays(userId)
            } else {
                // It's been more than a day user logged in, reset the counter to 1
                updateLastLoginDate(today)
                resetDays(userId)
            }
        }
    }
    private fun getLastLoginDate(): String? {
        return preferences.getString(Constants.LAST_LOGIN_DAY)
    }
    private fun updateLastLoginDate(date: String) {
        preferences.saveString(Constants.LAST_LOGIN_DAY, date)
    }
    private fun getYesterdayDate(simpleDateFormat: DateFormat, date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        return simpleDateFormat.format(calendar.time)
    }

    private fun incrementDays(userId: String) {
        val days = getConsecutiveDays(userId) + Constants.ONE
        updateConsecutiveDays(userId, days)
        Utils.updateFirebaseUserData(userId, days)
    }

    private fun resetDays(userId: String) {
        updateConsecutiveDays(userId, Constants.ONE)
        // Add your logic to update Firebase user data here
        Utils.updateFirebaseUserData(userId, Constants.ONE)
    }

    private fun updateConsecutiveDays(userId: String, days: Int) {
        // Update local preferences
        preferences.saveInt(Constants.CONSECUTIVE_DAYS, days)
    }

    private fun getConsecutiveDays(userId: String): Int {
        return preferences.getInt(Constants.CONSECUTIVE_DAYS)
    }

}
