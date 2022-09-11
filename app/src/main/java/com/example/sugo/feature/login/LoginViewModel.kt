package com.example.sugo.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(): ViewModel() {

    val Id = MutableLiveData<String>()
    val Pwd = MutableLiveData<String>()
}