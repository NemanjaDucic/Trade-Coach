package com.magma.tradecoach.ui.segmentIntro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityLoginBinding
import com.magma.tradecoach.ext.getTextTrimmed
import com.magma.tradecoach.ext.startActivityWithExtras
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
          this.startActivityWithExtras(RegisterActivity::class.java)
        }
        binding.imageButton.setOnClickListener {
            if (binding.passwordET.text.isNotEmpty() && binding.emailadressET.text.isNotEmpty()) {
                viewModel.login(binding.emailadressET.getTextTrimmed(),binding.passwordET.getTextTrimmed(),this)
            } else {
                Toast.makeText(this, "You must enter Password and Email", Toast.LENGTH_LONG).show()

            }
        }
        binding.forgotpasswordTV.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
    }
}
