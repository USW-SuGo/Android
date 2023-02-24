package com.sugo.app.feat.repository.repo.login

import com.sugo.app.feat.model.*
import com.sugo.app.feat.model.request.LoginFormat
import com.sugo.app.feat.model.request.email
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class LoginRemoteDataSource(private val apiService:SugoRetrofit):LoginDataSource {
    override suspend fun login(id: String, passWord: String): Response<Unit> {
        return apiService.login(LoginFormat(id,passWord))
    }

    override suspend fun findlogin(email: String): Response<Success> {
        return apiService.findlogin(email(email))
    }
}
