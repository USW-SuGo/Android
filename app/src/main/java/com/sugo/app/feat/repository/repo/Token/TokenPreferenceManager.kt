package com.sugo.app.feat.repository.repo.Token

import android.content.Context


class TokenPreferenceManager(context: Context) : TokenLocalDataSource {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    override fun saveAccessToken(token: String?) {
        prefs.edit().putString(ACCESS_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String? {
        return prefs.getString(Refresh_TOKEN, null)
    }

    override fun saveRefreshToken(token: String?) {
        prefs.edit().putString(Refresh_TOKEN, token).apply()
    }

    companion object {
        const val PREFS_NAME = "preferences"
        const val ACCESS_TOKEN = "accesstoken"
        const val Refresh_TOKEN = "refreshtoken"
    }
}