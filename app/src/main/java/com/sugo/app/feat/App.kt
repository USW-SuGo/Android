package com.sugo.app.feat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager

class App : Application(){

    override fun onCreate() {
        prefs = TokenPreferenceManager(applicationContext)
        super.onCreate()
    }

    companion object{
        lateinit var prefs : TokenPreferenceManager
        val loginform =MutableLiveData<Boolean>()
    }
}