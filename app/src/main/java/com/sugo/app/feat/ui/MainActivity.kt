package com.sugo.app.feat.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.sugo.app.R
import com.sugo.app.feat.App
import com.sugo.app.feat.ui.common.User
import com.sugo.app.feat.ui.common.User.loginform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Tokenvalue", App.prefs.getAccessToken().toString())
        Log.d("Tokenvalue", App.prefs.getRefreshToken().toString())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationView.itemIconTintList = null
        val navController =
            supportFragmentManager.findFragmentById(R.id.main_container)?.findNavController()


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
        navController?.let {
            bottomNavigationView.setupWithNavController(it)

        }
    }

}