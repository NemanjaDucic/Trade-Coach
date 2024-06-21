package com.magma.tradecoach.utilities

import android.annotation.SuppressLint

object SessionManager {
    @SuppressLint("StaticFieldLeak")
    private val prefSingleton = PrefSingleton.instance
    private const val ID_KEY = "id"
    private const val USERNAME_KEY = "username"


    fun getId(): String {
        return prefSingleton.getString(ID_KEY)
    }

    fun getUsername(): String {
        return prefSingleton.getString(USERNAME_KEY)
    }


}