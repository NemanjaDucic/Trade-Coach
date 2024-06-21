package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ConsecutiveDayChecker(private val context: Context) {

    private var preferences = PrefSingleton.instance
    private val constants = Constants
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
                constants.didShowLogin = true
            } else if (lastLoginDay == yesterday) {
                constants.didShowLogin = false
                updateLastLoginDate(today)
                incrementDays(userId)
            } else {
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
       updateFirebaseUserData(userId, days)
    }

    private fun resetDays(userId: String) {
        updateConsecutiveDays(userId, Constants.ONE)
        updateFirebaseUserData(userId, Constants.ONE)
    }

    private fun updateConsecutiveDays(userId: String, days: Int) {
        preferences.saveInt(Constants.CONSECUTIVE_DAYS, days)
    }

    private fun getConsecutiveDays(userId: String): Int {
        return preferences.getInt(Constants.CONSECUTIVE_DAYS)
    }
   private fun updateFirebaseUserData(userId: String, consecutiveDays: Int) {
        val databaseRef = FirebaseDatabase.getInstance().reference
        databaseRef.child("users").child(userId).child("streak").setValue(consecutiveDays)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    println("Firebase" +  "Error updating streak in Firebase")
                }
            }
    }

}
