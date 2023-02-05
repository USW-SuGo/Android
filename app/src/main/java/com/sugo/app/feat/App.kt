package com.sugo.app.feat

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager

class App : Application(){

    companion object{
      lateinit var prefs : TokenPreferenceManager
    }
    override fun onCreate() {
        prefs = TokenPreferenceManager(applicationContext)
        super.onCreate()

    }
}