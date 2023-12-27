package com.magma.tradecoach.utilities

import android.annotation.SuppressLint

object SessionManager {
    @SuppressLint("StaticFieldLeak")
    private val prefSingleton = PrefSingleton.instance ?: PrefSingleton()

    private const val ID_KEY = "id"
    private const val USERNAME_KEY = "username"
    private const val MY_NUMBERS_KEY = "myNumbers"


    fun getId(): String {
        return prefSingleton.getString(ID_KEY)
    }

    fun setId(id: String?) {
        if (id != null) {
            prefSingleton.saveString(ID_KEY, id)
        }
    }

    fun getUsername(): String {
        return prefSingleton.getString(USERNAME_KEY)
    }

//    fun setUsername(username: String?) {
//        prefSingleton.saveString(USERNAME_KEY, username)
//    }
//
//    fun getGoal(): Boolean {
//        return prefSingleton.getBool("goal" + DateTime.getFirebaseFormatted())
//    }
//
//    fun setGoal(b: Boolean) {
//        prefSingleton.saveBool("goal" + DateTime.getFirebaseFormatted(), b)
//    }

    fun saveMyNumbers(myNumbers: String) {
        prefSingleton.saveString(MY_NUMBERS_KEY, myNumbers)
    }

    fun getMyNumbers(): ArrayList<Int> {
        val myNumbers = ArrayList<Int>()
        val stringNumbers = prefSingleton.getString(MY_NUMBERS_KEY)
        val splitNumbers = stringNumbers.split(",")

        for (i in splitNumbers.indices)
            myNumbers.add(splitNumbers[i].toInt())

        return myNumbers
    }
//
//    fun getExtraBonus(): Boolean {
//        return prefSingleton.getBool("extra" + DateTime.getFirebaseFormatted())
//    }
//
//    fun setExtraBonus(b: Boolean) {
//        prefSingleton.saveBool("extra" + DateTime.getFirebaseFormatted(), b)
//    }

//    fun hasDeposited(): Boolean {
//        return prefSingleton.getInt(DEPOSIT_KEY + DateTime.getFirebaseFormatted()) >= 250
//    }
//
//    fun getDeposited(): Int {
//        return prefSingleton.getInt(DEPOSIT_KEY + DateTime.getFirebaseFormatted())
//    }
//
//    fun setDeposited(deposited: Int) {
//        val toSave = getDeposited() + deposited
//        prefSingleton.saveInt(DEPOSIT_KEY + DateTime.getFirebaseFormatted(), toSave)
//    }
}