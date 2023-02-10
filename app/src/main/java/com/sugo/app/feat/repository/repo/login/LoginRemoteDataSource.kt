package com.sugo.app.feat.repository.repo.login

import com.sugo.app.feat.App
import com.sugo.app.feat.model.*
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class LoginRemoteDataSource(private val apiService:SugoRetrofit):LoginDataSource {
    override suspend fun login(id: String,passWord: String): Response<Unit> {
        return apiService.login(LoginFormat(id,passWord))
    }

    override suspend fun findlogin(email: String): Response<Success> {
        return apiService.findlogin(email(email))
    }
}
private fun TokenHeadersText(originalHeaders: String?): Pair<String, String> {
    val tempHeaders = originalHeaders!!.split(", ")
    val accessToken = tempHeaders[1].replace("}", "")
    val refreshToken = tempHeaders[0].replace("{", "")
    return Pair(accessToken, refreshToken)
}