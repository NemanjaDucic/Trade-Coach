package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityGetStartedBinding
import com.magma.tradecoach.utilities.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedActivity:AppCompatActivity() {
    private lateinit var binding:ActivityGetStartedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners(){
        binding.getStartedButton.setOnClickListener {
            Utils.intent(this,WelcomeActivity::class.java,null)
        }
        binding.loginButton.setOnClickListener {
            Utils.intent(this,LoginActivity::class.java,null)

        }
    }
}