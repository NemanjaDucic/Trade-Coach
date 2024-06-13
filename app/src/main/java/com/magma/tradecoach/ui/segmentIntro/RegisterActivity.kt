package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityRegisterBinding
import com.magma.tradecoach.ext.stringText
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity:AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    private var countryCode = "US"

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, callback)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        listeners()
    }

    private fun listeners() {
        binding.countyCodePicker.setOnCountryChangeListener {
            countryCode = binding.countyCodePicker.selectedCountryName
        }

        binding.btRegister.setOnClickListener {
            viewModel.register(
                binding.usernameET.stringText(),
                countryCode,
                binding.emailET.stringText(),
                binding.passwordET.stringText(),
                this
            )
        }

        binding.loginTV.setOnClickListener {
            Utils.intent(this, LoginActivity::class.java)
        }
    }
}
