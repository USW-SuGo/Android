package com.example.sugo_mvc.util

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class Preference (context: Context){


    private val prefs=context.getSharedPreferences("asdasd",MODE_PRIVATE)

    var AccessToken:String?
        get() = prefs.getString(ACCESS_TOKEN,null)
        set(value){
            prefs.edit().putString(ACCESS_TOKEN,value).apply()
        }
    fun saveAccessToken(value: String) = setString(ACCESS_TOKEN, value)
    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
    fun saveRefreshToken(value: String) = setString(REFRESH_TOKEN, value)
    var RefreshToken:String?
        get() = prefs.getString(REFRESH_TOKEN,null)
        set(value){
            prefs.edit().putString(REFRESH_TOKEN,value).apply()
        }
}

private const val ACCESS_TOKEN = "Accesstoken"
private const val REFRESH_TOKEN = "RefreshToken"

class App : Application(){
    companion object{
        lateinit var prefs:Preference
    }
    override fun onCreate() {
        prefs=Preference(applicationContext)
        super.onCreate()
}}

