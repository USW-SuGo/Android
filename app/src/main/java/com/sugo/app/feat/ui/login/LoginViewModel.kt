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
import com.sugo.app.feat.ui.common.TokenHeadersText
import kotlinx.coroutines.launch

class LoginViewModel(
    val loginRepository: LoginRepository,
    val tokenPreferenceManager: TokenPreferenceManager
) : ViewModel() {

    fun login(id: String, passWord: String) = viewModelScope.launch {
        val response = loginRepository.login(id, passWord)
        val originalHeaders = response.headers().get("authorization")
        if (response.code() == 200) {
            val (accessToken, refreshToken) = TokenHeadersText(originalHeaders)
            saveAccessToken(accessToken)
            saveRefreshToken(refreshToken)
        }
    }
    private fun saveAccessToken(token: String) = viewModelScope.launch {
        tokenPreferenceManager.saveAccessToken(token)
    }
    private fun saveRefreshToken(token: String) = viewModelScope.launch {
        tokenPreferenceManager.saveRefreshToken(token)
    }
}