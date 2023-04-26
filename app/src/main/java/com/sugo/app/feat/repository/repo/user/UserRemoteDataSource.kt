package com.sugo.app.feat.repository.repo.user

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.User
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.detail.DetailDataSource
import retrofit2.Response

class UserRemoteDataSource (private val apiService: SugoRetrofit) : UserDataSource {

    override suspend fun getUser(userId: Long): Response<User> {
        return apiService.getUser(userId)
    }

}