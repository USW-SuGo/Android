package com.sugo.app.feat.repository.repo.mypage

import com.sugo.app.feat.model.MyPage
import com.sugo.app.feat.model.Success
import retrofit2.Response

class MyPageRepository(private val myPageDataSource: MyPageRemoteDataSource) {

    suspend fun getMyPage(): Response<MyPage> {
        return myPageDataSource.getMyPage()
    }

    suspend fun upPost(productPostId: Long): Response<Success> {
        return myPageDataSource.upPost(productPostId)
    }
}
