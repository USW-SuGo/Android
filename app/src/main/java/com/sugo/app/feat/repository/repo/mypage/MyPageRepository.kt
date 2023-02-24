package com.sugo.app.feat.repository.repo.mypage

import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.model.Success
import retrofit2.Response

class MyPageRepository(private val myPageDataSource: MyPageRemoteDataSource) {

    suspend fun getMyPage(): Response<MyPage> {
        return myPageDataSource.getMyPage()
    }

    suspend fun upPost(productPostId: Long): Response<Success> {
        return myPageDataSource.upPost(productPostId)
    }
    suspend fun deletePost(productPostId: Long): Response<Success> {
        return myPageDataSource.deletePost(productPostId)
    }
    suspend fun postClose(productPostId: Long): Response<Success> {
        return myPageDataSource.postClose(productPostId)
    }
}
