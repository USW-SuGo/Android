package com.sugo.app.feat.ui.join.inputUser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sugo.app.R
import com.sugo.app.databinding.ActivityJoinBinding
import com.sugo.app.feat.ui.common.ViewModelFactory

class JoinActivity : AppCompatActivity() {
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.viewmodel = viewModel
    }
}
