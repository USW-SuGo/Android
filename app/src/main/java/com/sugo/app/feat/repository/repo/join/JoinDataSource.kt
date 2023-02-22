package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.*
import retrofit2.Response

interface JoinDataSource {

    suspend fun checkLoginId(loginId: LoginId) : Response<Success>
    suspend fun checkEmail(email: email) :Response<Success>
    suspend fun join(userSign: UserSign) :Response<JoinCheck>
    suspend fun checkPayLoad(payLoad: PayLoad) :Response<Success>
}