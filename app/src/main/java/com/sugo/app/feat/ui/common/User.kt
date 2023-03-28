package com.sugo.app.feat.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sugo.app.feat.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object User {
    val loginform=MutableLiveData<Boolean>(App.prefs.getAccessToken()!="")

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            loginform.value== false
            App.prefs.saveAccessToken("")
            App.prefs.saveRefreshToken("")
        }
    }
}