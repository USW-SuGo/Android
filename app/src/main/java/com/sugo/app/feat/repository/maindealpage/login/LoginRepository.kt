package com.sugo.app.feat.repository.maindealpage.login

import android.util.Log
import androidx.datastore.dataStore
import com.sugo.app.feat.model.LoginFormat
import com.sugo.app.feat.model.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository(
    private val loginDataSource:LoginRemoteDataSource,
) {

    suspend fun login(loginFormat: LoginFormat)=
        withContext(Dispatchers.IO){
            val result = loginDataSource.login(loginFormat)
            result.body()?.also {
                Log.d("token",it.toString())
            }
            return@withContext
        }
}