package com.sugo.app.feat.repository.repo.user

import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.model.response.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserDataSource {
    suspend fun getUser(userId: Long): Response<User>
    suspend fun setManner(mannerTarget: MannerTarget): Response<Success>
    fun getUserPageProduct(userId: Long): Flow<PagingData<DealProduct>>
    fun getUserCompletePageProduct(userId: Long): Flow<PagingData<DealProduct>>
}