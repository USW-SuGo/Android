package com.sugo.app.feat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.model.request.FcmToken
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager
import com.sugo.app.feat.repository.repo.login.LoginRepository
import com.sugo.app.feat.ui.common.Event
import com.sugo.app.feat.ui.common.TokenHeadersText
import com.sugo.app.feat.ui.common.User
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
                User.loginform.value = true
                val (accessToken, refreshToken) = TokenHeadersText(originalHeaders)
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
            }
        }
    }
     fun sendFCM(fcmToken: FcmToken) = viewModelScope.launch{
         loginRepository.sendFCM(fcmToken)
     }
    private fun saveAccessToken(token: String) = viewModelScope.launch {
        tokenPreferenceManager.saveAccessToken(token)
    }
    private fun saveRefreshToken(token: String) = viewModelScope.launch {
        tokenPreferenceManager.saveRefreshToken(token)
    }
}