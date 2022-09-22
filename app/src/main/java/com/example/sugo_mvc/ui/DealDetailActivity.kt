package com.example.sugo_mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.databinding.ActivityMainBinding

class DealDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDealDetailBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}