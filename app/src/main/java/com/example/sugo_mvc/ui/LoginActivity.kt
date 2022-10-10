package com.example.sugo_mvc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupbtn.setOnClickListener {
            val intent = Intent(applicationContext,JoinActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {

        }
    }
}