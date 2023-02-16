package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.LoginId
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.email
import retrofit2.Response

class JoinRepository(private val joinDataSource: JoinRemoteDataSource) {
    suspend fun  checkLoginId(loginId: LoginId): Response<Success> {
        return  joinDataSource.checkLoginId(loginId)
    }
    suspend fun checkEmail(email: email): Response<Success> {
        return joinDataSource.checkEmail(email)
    }


}