package com.sugo.app.feat.repository.repo.user

import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.model.response.User
import com.sugo.app.feat.repository.repo.detail.DetailRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun getUser(userId: Long): Response<User> {
        return userRemoteDataSource.getUser(userId)
    }
    suspend fun setManner(mannerTarget: MannerTarget): Response<Success>{
        return userRemoteDataSource.setManner(mannerTarget)
    }

    fun getUserPageProduct(userId: Long): Flow<PagingData<DealProduct>>{
        return userRemoteDataSource.getUserPageProduct(userId)
    }
    fun getUserCompletePageProduct(userId: Long): Flow<PagingData<DealProduct>>{
        return userRemoteDataSource.getUserCompletePageProduct(userId)
    }

}