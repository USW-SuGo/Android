package com.sugo.app.feat.repository.repo.login

import com.sugo.app.feat.model.*
import com.sugo.app.feat.model.request.FCMToken
import com.sugo.app.feat.model.response.NoteRoom
import retrofit2.Response

interface LoginDataSource {

    suspend fun login(id: String, passWord: String) :Response<Unit>
    suspend fun findlogin(email: String) :Response<Success>

    suspend fun sendFCM(fcmToken: FCMToken) :Response<Success>



}