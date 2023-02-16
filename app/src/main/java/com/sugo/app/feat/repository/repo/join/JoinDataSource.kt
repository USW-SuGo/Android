package com.sugo.app.feat.repository.repo.join

import com.sugo.app.feat.model.LoginId
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.email
import retrofit2.Response

interface JoinDataSource {

    suspend fun checkLoginId(loginId: LoginId) : Response<Success>
    suspend fun checkEmail(email: email) :Response<Success>

}