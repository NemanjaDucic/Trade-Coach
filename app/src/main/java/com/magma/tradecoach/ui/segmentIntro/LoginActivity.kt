package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityLoginBinding
import com.magma.tradecoach.ui.segmentIntro.RegisterActivity
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity:AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val utils = Utils(this)
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners(){
        binding.registerbotTV.setOnClickListener {
            utils.intent(this, RegisterActivity::class.java,null)
        }
        binding.imageButton.setOnClickListener {
            viewModel.login(utils.getETText(binding.emailadressET),utils.getETText(binding.passwordET),this)
        }

    }
}
