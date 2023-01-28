package com.sugo.app.feat.ui.common

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class ThreetenApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}