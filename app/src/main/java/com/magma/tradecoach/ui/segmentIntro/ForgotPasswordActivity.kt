package com.magma.tradecoach.ui.segmentIntro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.magma.tradecoach.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity:AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        init()
    }
    private fun init(){
        binding.imageButton.setOnClickListener {
            val email = binding.ressetpasswordET.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this,"Email must not be empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show().let {
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)

                        }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}