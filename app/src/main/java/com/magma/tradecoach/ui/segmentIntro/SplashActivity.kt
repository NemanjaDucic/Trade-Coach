package com.magma.tradecoach.ui.segmentIntro

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.ui.segmentMain.MainActivity
import com.magma.tradecoach.databinding.ActivitySplashBinding
import com.magma.tradecoach.utilities.PrefSingleton
import com.magma.tradecoach.utilities.Utils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity:AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startActivity()
    }
    private fun startActivity(){

        Handler().postDelayed({
            if (PrefSingleton.getInstance().isLogged()){
                Utils.intent(this, MainActivity::class.java,null)

            } else {
                Utils.intent(this,GetStartedActivity::class.java,null)
            }

        }, 1000)
    }

}