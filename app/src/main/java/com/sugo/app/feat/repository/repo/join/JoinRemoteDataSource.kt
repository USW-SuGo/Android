package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.LoginId
import com.sugo.app.feat.model.request.PayLoad
import com.sugo.app.feat.model.request.UserSign
import com.sugo.app.feat.model.request.email
import com.sugo.app.feat.model.response.JoinCheck
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class JoinRemoteDataSource(private val apiService: SugoRetrofit) : JoinDataSource {
    override suspend fun checkLoginId(loginId: LoginId): Response<Success> {
        return apiService.checkLoginId(loginId)
    }

    override suspend fun checkEmail(email: email): Response<Success> {
        return apiService.checkEmail(email)
    }

    override suspend fun join(userSign: UserSign): Response<JoinCheck> {
        return apiService.join(userSign)
    }

    override suspend fun checkPayLoad(payLoad: PayLoad): Response<Success> {
        return apiService.checkPayLoad(payLoad)
    }
}