package com.sugo.app.feat.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.App
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager
import com.sugo.app.feat.repository.repo.login.LoginRepository
import com.sugo.app.feat.ui.common.Event
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.TokenHeadersText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    val loginRepository: LoginRepository,
    val tokenPreferenceManager: TokenPreferenceManager
) : ViewModel() {

    private val _openDealEvent = MutableLiveData<Event<Long>>()
    val openDealEvent: LiveData<Event<Long>> = _openDealEvent

    fun login(id: String, passWord: String) = viewModelScope.launch {
        val response = loginRepository.login(id, passWord)
        val originalHeaders = response.headers().get("authorization")
        if (response.code() == 200) {
            CoroutineScope(Dispatchers.Main).launch {
                App.loginform.value = true
            }
            val (accessToken, refreshToken) = TokenHeadersText(originalHeaders)
            App.prefs.saveAccessToken(accessToken)
            App.prefs.saveRefreshToken(refreshToken)
//            saveAccessToken(accessToken)
//            saveRefreshToken(refreshToken)
        }
    }
//    private fun saveAccessToken(token: String) = viewModelScope.launch {
//        tokenPreferenceManager.saveAccessToken(token)
//    }
//    private fun saveRefreshToken(token: String) = viewModelScope.launch {
//        tokenPreferenceManager.saveRefreshToken(token)
//    }
}