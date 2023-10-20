package com.magma.tradecoach

import android.app.Application
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.magma.tradecoach.utilities.PrefSingleton

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PrefSingleton()

        val prefSingleton = PrefSingleton.getInstance()
        if (prefSingleton == null) {
            val intent = Intent(this,MainActivity::class.java)

            startActivity(intent)
        }
    }
}