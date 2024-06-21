package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityGetStartedBinding
import com.magma.tradecoach.ext.startActivityWithExtras
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedActivity:AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        this.startActivityWithExtras(WelcomeActivity::class.java)

    }

    private fun listeners() {
        binding.getStartedButton.setOnClickListener {
            this.startActivityWithExtras(WelcomeActivity::class.java)
        }
        binding.loginButton.setOnClickListener {
            this.startActivityWithExtras(LoginActivity::class.java)
        }
    }
}