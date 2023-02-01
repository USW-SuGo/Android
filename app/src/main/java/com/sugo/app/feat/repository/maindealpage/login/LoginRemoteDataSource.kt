package com.sugo.app.feat.repository.maindealpage.login

import com.sugo.app.feat.model.LoginFormat
import com.sugo.app.feat.model.Token
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class LoginRemoteDataSource(private val apiService:SugoRetrofit):LoginDataSource {
    override suspend fun login(loginFormat: LoginFormat): Response<Token> {
        return apiService.login(loginFormat)
    }

}