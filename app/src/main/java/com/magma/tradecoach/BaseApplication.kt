package com.magma.tradecoach

import android.app.Application
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.magma.tradecoach.utilities.PrefSingleton
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): BaseApplication {
            return instance as BaseApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PrefSingleton()
    }
}