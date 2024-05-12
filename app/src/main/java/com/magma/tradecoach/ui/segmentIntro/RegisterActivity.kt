package com.magma.tradecoach.ui.segmentIntro

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityRegisterBinding
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity:AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
    private val utils = Utils
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
        binding.imageButton.setOnClickListener {
            viewModel.register(
                utils.getETText(binding.usernameET),
                countryCode,
                utils.getETText(binding.emailET),
                utils.getETText(binding.passwordET),
                this
            )
        }

        binding.loginTV.setOnClickListener {
            Utils.intent(this, LoginActivity::class.java)
        }

        binding.countyCodePicker.setShowPhoneCode(false)
        binding.countyCodePicker.setCcpDialogShowPhoneCode(false)
        binding.countyCodePicker.showFullName(true)
        binding.countyCodePicker.contentColor = Color.WHITE
        binding.countyCodePicker.setOnCountryChangeListener {
            countryCode = binding.countyCodePicker.selectedCountryName
        }
    }
}
