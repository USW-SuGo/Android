package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.LoginId
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.email
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class JoinRemoteDataSource(private val apiService:SugoRetrofit):JoinDataSource {
    override suspend fun checkLoginId(loginId: LoginId): Response<Success> {
        return apiService.checkLoginId(loginId)
    }

    override suspend fun checkEmail(email: email): Response<Success> {
        return apiService.checkEmail(email)
    }

}