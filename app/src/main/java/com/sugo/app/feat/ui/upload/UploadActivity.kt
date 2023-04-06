package com.sugo.app.feat.ui.upload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sugo.app.R
import com.sugo.app.feat.ui.MainActivity
import com.sugo.app.feat.ui.login.LoginActivity

class UploadActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val navController =
            supportFragmentManager.findFragmentById(R.id.upload_container)?.findNavController()
        navController.let {  (it)}
    }
}