package com.sugo.app.feat.repository.repo.mypage

import com.sugo.app.feat.model.MyPage
import com.sugo.app.feat.model.Success
import retrofit2.Response

interface MyPageDataSource {

    suspend fun getMyPage():Response<MyPage>
    suspend fun upPost(productPostId:Long):Response<Success>
    suspend fun deletePost(productPostId:Long):Response<Success>
}