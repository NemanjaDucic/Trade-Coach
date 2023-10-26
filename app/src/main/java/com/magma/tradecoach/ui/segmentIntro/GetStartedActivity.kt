package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.utilities.Utilities
import com.magma.tradecoach.databinding.ActivityGetStartedBinding
import com.magma.tradecoach.utilities.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedActivity:AppCompatActivity() {
    private lateinit var binding:ActivityGetStartedBinding

    private val util = Utils(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners(){
        binding.getStartedButton.setOnClickListener {
            util.intent(this,WelcomeActivity::class.java,null)
        }
        binding.loginButton.setOnClickListener {
            util.intent(this,LoginActivity::class.java,null)

        }
    }
}