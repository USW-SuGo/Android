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
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.sugo.app.R
import com.sugo.app.feat.App
import com.sugo.app.feat.common.MyFirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(App.loginform.value==null) App.loginform.value=false
        getInstanceFCm()
        Log.d("Tokenvalue",App.prefs.getAccessToken().toString())
        Log.d("Tokenvalue",App.prefs.getRefreshToken().toString())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationView.itemIconTintList=null
        val navController = supportFragmentManager.findFragmentById(R.id.main_container)?.findNavController()
        if (App.loginform.value==null){
        }

        navController?.let {
            bottomNavigationView.setupWithNavController(it)

        }
    }

    private fun getInstanceFCm() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            App.prefs.saveFCM(token)
            // Log and toast
            val msg = token.toString()
            Log.d("FCM", msg)
            Log.d("FCM", App.prefs.getFCM().toString())
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }
}