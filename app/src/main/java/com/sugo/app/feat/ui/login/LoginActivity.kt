package com.sugo.app.feat.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sugo.app.R
import com.sugo.app.databinding.ActivityLoginBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.join.inputUser.JoinActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory(this)}
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.loginviewmodel=viewModel
        binding.btnAssign.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

}
