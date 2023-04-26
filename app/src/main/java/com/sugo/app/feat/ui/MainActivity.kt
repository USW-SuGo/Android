package com.sugo.app.feat.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.sugo.app.R
import com.sugo.app.databinding.ActivityMainBinding
import com.sugo.app.feat.App
import com.sugo.app.feat.ui.common.KeepStateNavigator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFCM()


        val navigationFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navigationFragment.navController
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController)
        binding.mainBottomNavigation.setOnItemReselectedListener { }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_deal || destination.id == R.id.navigation_message || destination.id == R.id.navigation_mypage)
                binding.mainBottomNavigation.visibility = View.VISIBLE
            else
                binding.mainBottomNavigation.visibility = View.GONE
        }

    }

    private fun getFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            App.prefs.saveFCM(token)
            Log.d("FCMTOKEN", App.prefs.getFCM().toString())
        })
    }

}