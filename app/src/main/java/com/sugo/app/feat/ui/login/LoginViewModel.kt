package com.sugo.app.feat.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager
import com.sugo.app.feat.repository.repo.login.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    val loginRepository: LoginRepository,
    val tokenPreferenceManager: TokenPreferenceManager
) : ViewModel() {

    private val _loginId = MutableLiveData<String>()
    val loginId: LiveData<String> = _loginId
    private val _loginPassWord = MutableLiveData<String>()
    val loginPassWord: LiveData<String> = _loginPassWord

    fun login(id: String, passWord: String) = viewModelScope.launch {
        val response = loginRepository.login(id, passWord)
        val a = response.headers()
        if (response.code() == 200) {
            saveAccessToken(a.toString())
        }
    }

    fun findlogin(email: String) = viewModelScope.launch {
        val response = loginRepository.findlogin(email)
        if (response.isSuccessful) Log.d("ads", "asdasd") else Log.d("ads", "asdasd")
    }

    fun saveAccessToken(token: String) = viewModelScope.launch {
        tokenPreferenceManager.saveAccessToken(token)
    }
}