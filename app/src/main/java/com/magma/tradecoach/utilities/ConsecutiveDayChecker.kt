package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ConsecutiveDayChecker(private val context: Context) {
    //TODO Zasto ovde pravimo nove shared prefs, ako imamo pref singleton?
    //TODO Cuvanje svih ovih stvari treba da ima key koji je konstanta, a ne da se hardcoduje string
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
        val sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("last_login_day", date)
        editor.apply()
    }

    private fun getLastLoginDate(): String? {
        val sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        return sharedPref.getString("last_login_day", null)
    }

    private fun getConsecutiveDays(): Int {
        val sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        return sharedPref.getInt("num_consecutive_days", 0)
    }

    private fun incrementDays() {
        val days = getConsecutiveDays() + 1
        val sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("num_consecutive_days", days)
        editor.apply()
    }

    private fun resetDays() {
        val days = 1
        val sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("num_consecutive_days", days)
        editor.apply()
    }

    fun getStreak(): Int {
        return getConsecutiveDays()
    }
}
