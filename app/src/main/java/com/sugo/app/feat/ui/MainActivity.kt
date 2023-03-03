package com.sugo.app.feat.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sugo.app.R
import com.sugo.app.feat.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Tokenvalue",App.prefs.getAccessToken().toString())
        Log.d("Tokenvalue",App.prefs.getRefreshToken().toString())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationView.itemIconTintList=null
        val navController = supportFragmentManager.findFragmentById(R.id.main_container)?.findNavController()
        if (App.loginform.value==null){

        }
//        CoroutineScope(Dispatchers.Main).launch {
//            App.loginform.value = true
//            Log.d("MainActivity",App.loginform.value.toString())
//        }
        navController?.let {
            bottomNavigationView.setupWithNavController(it)

        }
    }
}