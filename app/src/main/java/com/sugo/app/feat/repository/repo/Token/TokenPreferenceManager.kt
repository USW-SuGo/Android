package com.sugo.app.feat.repository.repo.Token

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.sugo.app.feat.App
import com.sugo.app.feat.network.SugoRetrofit


class TokenPreferenceManager(context: Context) : TokenLocalDataSource {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? {

        return prefs.getString(ACCESS_TOKEN, null)
    }

    @SuppressLint("CommitPrefEdits")
    override fun saveAccessToken(token: String?) {
        prefs.edit().putString(ACCESS_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String? {
        return prefs.getString(Refresh_TOKEN, null)
    }

    override fun saveRefreshToken(token: String?) {
        prefs.edit().putString(Refresh_TOKEN, token).apply()
    }
    override fun savenote(token: String?) {
        prefs.edit().putString(TEST_NOTE, token).apply()
    }
    override fun getnote(): String? {
        return prefs.getString(TEST_NOTE, null)
    }

    private companion object {
        const val TEST_NOTE="test"
        const val PREFS_NAME = "preferences"
        const val ACCESS_TOKEN = "accesstoken"
        const val Refresh_TOKEN = "refreshtoken"
    }
}