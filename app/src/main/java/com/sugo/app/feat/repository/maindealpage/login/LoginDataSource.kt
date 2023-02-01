package com.sugo.app.feat.repository.maindealpage.login

import com.sugo.app.feat.model.LoginFormat
import com.sugo.app.feat.model.Token
import retrofit2.Response

interface LoginDataSource {

    suspend fun login(loginFormat: LoginFormat) :Response<Token>
}