package com.sugo.app.feat.repository.repo.login

import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.FcmToken
import retrofit2.Response

class LoginRepository(
    private val loginDataSource: LoginRemoteDataSource
) {

    suspend fun login(id: String, passWord: String): Response<Unit> {
        return loginDataSource.login(id, passWord)
    }


    suspend fun findlogin(email: String): Response<Success> {
        return loginDataSource.findlogin(email)
    }

    suspend fun sendFCM(fcmToken: FcmToken): Response<Success> {
        return loginDataSource.sendFCM(fcmToken)
    }


}