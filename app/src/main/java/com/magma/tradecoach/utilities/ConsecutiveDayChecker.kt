package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.magma.tradecoach.BaseApplication
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ConsecutiveDayChecker(private val context: Context) {

    private var preferences = PrefSingleton.instance
    @SuppressLint("SimpleDateFormat")
    fun onUserLogin() {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val today = dateFormat.format(date)
        val lastLoginDay = getLastLoginDate()

        val yesterday = getYesterdayDate(dateFormat, date)

        if (lastLoginDay == null) {
            // user logged in for the first time
            updateLastLoginDate(today)
            incrementDays()
        }
        // Check for if bonus should be awarded
//        if (getStreak() % 3 == 0) {
//            //add bonus
//            //ce vidimo kako ide
//        }
          else {
            if (lastLoginDay == today) {
                // User logged in the same day, do nothing
            } else if (lastLoginDay == yesterday) {
                // User logged in consecutive days, add 1
                updateLastLoginDate(today)
                incrementDays()
            } else {
                // It's been more than a day user logged in, reset the counter to 1
                updateLastLoginDate(today)
                resetDays()
            }
        }
    }

    private fun getYesterdayDate(simpleDateFormat: DateFormat, date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        return simpleDateFormat.format(calendar.time)
    }


    private fun updateLastLoginDate(date: String) {
        preferences.saveString(Constants.LAST_LOGIN_DAY, date)
    }

    private fun getLastLoginDate(): String? {
        return preferences.getString(Constants.LAST_LOGIN_DAY)
    }

    private fun getConsecutiveDays(): Int {
        return preferences.getInt(Constants.CONSECUTIVE_DAYS)
    }

    private fun incrementDays() {
        val days = getConsecutiveDays() + Constants.ONE
        preferences.saveInt(Constants.CONSECUTIVE_DAYS,days)
    }

    private fun resetDays() {
        preferences.saveInt(Constants.CONSECUTIVE_DAYS,Constants.ONE)
    }

    fun getStreak(): Int {
        return getConsecutiveDays()
    }
}
