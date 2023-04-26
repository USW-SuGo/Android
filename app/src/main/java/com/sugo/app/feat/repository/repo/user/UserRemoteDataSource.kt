package com.sugo.app.feat.repository.repo.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.model.response.User
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.detail.DetailDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

class UserRemoteDataSource(private val apiService: SugoRetrofit) : UserDataSource {

    override suspend fun getUser(userId: Long): Response<User> {
        return apiService.getUser(userId)
    }

    override suspend fun setManner(mannerTarget: MannerTarget): Response<Success> {
        return apiService.setManner(mannerTarget)
    }
    override fun getUserPageProduct(userId: Long): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            ProductUserPagingDataSource(apiService,userId)
        }.flow

    override fun getUserCompletePageProduct(userId: Long): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            ProdcutCompleteUserDataSource(apiService,userId)
        }.flow
}