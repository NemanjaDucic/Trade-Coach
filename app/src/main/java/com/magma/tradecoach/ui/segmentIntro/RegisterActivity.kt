package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityLoginBinding
import com.magma.tradecoach.databinding.ActivityRegisterBinding
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity:AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
    private val utils = Utils(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners(){
        binding.imageButton.setOnClickListener {
            viewModel.register(utils.getETText(binding.usernameET),utils.getETText(binding.countryET),utils.getETText(binding.emailET),utils.getETText(binding.passwordET),this)
        }
        binding.loginTV.setOnClickListener {
            onBackPressed()
            //Mislim da je bolje da se koristi ovo dole, onBackPressed je deprecated
            //super.onBackPressedDispatcher
        }
        binding.countyCodePicker.setShowPhoneCode(false)
        binding.countyCodePicker.setCcpDialogShowPhoneCode(false)
        binding.countyCodePicker.showFullName(true)
        binding.countyCodePicker.setOnCountryChangeListener {
            binding.countryET.setText(binding.countyCodePicker.selectedCountryName)
        }

        }

    }
