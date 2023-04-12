package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.LoginId
import com.sugo.app.feat.model.request.PayLoad
import com.sugo.app.feat.model.request.UserSign
import com.sugo.app.feat.model.request.email
import com.sugo.app.feat.model.response.JoinCheck
import retrofit2.Response

class JoinRepository(private val joinDataSource: JoinRemoteDataSource) {
    suspend fun checkLoginId(loginId: LoginId): Response<Success> {
        return joinDataSource.checkLoginId(loginId)
    }

    suspend fun checkEmail(email: email): Response<Success> {
        return joinDataSource.checkEmail(email)
    }

    suspend fun join(userSign: UserSign): Response<JoinCheck> {
        return joinDataSource.join(userSign)
    }

    suspend fun checkPayLoad(payLoad: PayLoad): Response<Success> {
        return joinDataSource.checkPayLoad(payLoad)
    }
}