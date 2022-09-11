package com.example.sugo.feature.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sugo.R
import com.example.sugo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel
        get() {
            TODO()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}