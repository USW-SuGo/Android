package com.sugo.app.feat.repository.repo.mypage

import com.sugo.app.feat.model.MyPage
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class MyPageRemoteDataSource(private val apiService:SugoRetrofit):MyPageDataSource {
    override suspend fun getMyPage(): Response<MyPage> {
        return apiService.getMyPage()
    }

    override suspend fun upPost(productPostId:Long): Response<Success> {
        return apiService.upPost(ProductPostId(productPostId) )
    }

    override suspend fun deletePost(productPostId: Long): Response<Success> {
        return apiService.deletePost(ProductPostId(productPostId) )
    }
}