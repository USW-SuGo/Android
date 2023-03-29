package com.sugo.app.feat.repository.repo.login

import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.App
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.FcmToken
import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.ui.common.TokenHeadersText
import com.sugo.app.feat.ui.common.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository(
    private val loginDataSource: LoginRemoteDataSource
) {

        suspend fun login(id: String, passWord: String): Response<Unit> {
        return loginDataSource.login(id, passWord)
    }


suspend fun findlogin(email: String): Response<Success> {
    return loginDataSource.findlogin(email)
}

suspend fun sendFCM(fcmToken: FcmToken): Response<Success> {
    return loginDataSource.sendFCM(fcmToken)
}



}