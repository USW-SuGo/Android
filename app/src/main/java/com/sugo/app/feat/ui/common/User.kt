package com.sugo.app.feat.ui.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sugo.app.feat.App
import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.network.SugoRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object User {

    val loginform=MutableLiveData<Boolean>(App.prefs.getAccessToken()!=""&& App.prefs.getAccessToken()!=null)

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            loginform.value= false
            Log.d("LoginTest", loginform.value.toString())
            App.prefs.saveAccessToken("")
            App.prefs.saveRefreshToken("")
        }
    }
}