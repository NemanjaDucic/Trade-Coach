package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PrefSingleton() {

    private lateinit var mContext: Context
    private lateinit var mMyPreferences: SharedPreferences

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: PrefSingleton? = null

        @JvmStatic
        fun getInstance(): PrefSingleton {
            if (instance == null) {
                instance = PrefSingleton()
            }
            return instance!!
        }
    }

    fun initialize(ctxt: Context) {
        mContext = ctxt
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
    }

    fun saveString(key: String, value: String) {
        val e = mMyPreferences.edit()
        e.putString(key, value)
        e.apply()
    }

    fun getString(key: String): String {
        return mMyPreferences.getString(key, "") ?: ""
    }

    fun saveInt(key: String, value: Int) {
        val e = mMyPreferences.edit()
        e.putInt(key, value)
        e.apply()
    }

    fun getInt(key: String): Int {
        return mMyPreferences.getInt(key, 0)
    }

    fun saveBool(key: String, value: Boolean) {
        val e = mMyPreferences.edit()
        e.putBoolean(key, value)
        e.apply()
    }

    fun getBool(key: String): Boolean {
        return mMyPreferences.getBoolean(key, false)
    }
}
