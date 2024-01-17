package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityLoginBinding
import com.magma.tradecoach.utilities.Constants
import com.magma.tradecoach.utilities.PrefSingleton
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity:AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners(){
        binding.registerbotTV.setOnClickListener {

            Utils.intent(this, RegisterActivity::class.java,null)
        }
        binding.imageButton.setOnClickListener {

            viewModel.login(Utils.getETText(binding.emailadressET),Utils.getETText(binding.passwordET),this)
        }

    }
}
