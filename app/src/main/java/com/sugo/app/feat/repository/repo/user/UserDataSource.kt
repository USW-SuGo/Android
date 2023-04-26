package com.sugo.app.feat.repository.repo.user

import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.model.response.User
import retrofit2.Response

interface UserDataSource {
    suspend fun getUser(userId: Long): Response<User>
    suspend fun setManner(mannerTarget: MannerTarget): Response<Success>
}