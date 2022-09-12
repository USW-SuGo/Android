package com.example.sugo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.sugo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var container = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bottomBar = binding.bottomNav
        container = binding.mainContainer.id
    supportFragmentManager.beginTransaction().replace(container, DealFragment())
        .commitAllowingStateLoss()

    bottomBar.setOnItemSelectedListener {
        getFragment(it)
    }
    bottomBar.setOnItemReselectedListener {
        Log.d("Main","menu reselected")
    }
    }
        private fun getFragment(menuItem: MenuItem): Boolean {
            when(menuItem.itemId){
                R.id.menuDeal -> {
                    supportFragmentManager.beginTransaction().replace(container,DealFragment())
                        .commitAllowingStateLoss()
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(container, DealFragment())
                        .commitAllowingStateLoss()
                }
            }
            return true
        }
}
