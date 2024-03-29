package com.sugo.app.feat.ui.upload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.sugo.app.R

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val navController =
            supportFragmentManager.findFragmentById(R.id.upload_container)?.findNavController()
        navController.let { (it) }
    }
}