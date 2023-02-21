package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.*
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class JoinRemoteDataSource(private val apiService:SugoRetrofit):JoinDataSource {
    override suspend fun checkLoginId(loginId: LoginId): Response<Success> {
        return apiService.checkLoginId(loginId)
    }

    override suspend fun checkEmail(email: email): Response<Success> {
        return apiService.checkEmail(email)
    }

    override suspend fun join(userSign: UserSign): Response<JoinCheck> {
        return apiService.join(userSign)
    }

}