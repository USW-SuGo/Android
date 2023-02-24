package com.sugo.app.feat

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager

class App : Application(){

    companion object{
      lateinit var prefs : TokenPreferenceManager
      val loginform =MutableLiveData<Boolean>()
    }
    override fun onCreate() {
        prefs = TokenPreferenceManager(applicationContext)
        super.onCreate()

    }
}