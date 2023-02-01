package com.sugo.app.feat.repository.maindealpage.login

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sugo.app.feat.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStore(
    private var token1 : Token,
    private val context: Context
) {
    object PreferenceKey{
        val ACCESS_TOKEN = stringPreferencesKey("AccessToken")
        val REFRESH_TOKEN = stringPreferencesKey("RefreshToken")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
    }


    private val Context.loginCheckDataStore by preferencesDataStore("login_check")
    private val Context.tokenDataStore by preferencesDataStore("token_dataStore")

    suspend fun saveToken(token: List<String>) {
        if (token.isNotEmpty()) {
            context.tokenDataStore.edit { prefs ->
                prefs[PreferenceKey.ACCESS_TOKEN] = token.first()
                prefs[PreferenceKey.REFRESH_TOKEN] = token.last()
            }
        }
    }

    suspend fun getToken(): Flow<List<String>> {
        return context.tokenDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs.asMap().values.toList().map {
                    it.toString()
                }
            }
    }
    fun setToken(token: String) {
        val tempToken = token!!.split(",")
        val accessToken = tempToken[1].replace("}", "")
        val refreshToken = tempToken[0].replace("{","")
        token1 = Token(accessToken, refreshToken)
    }
    suspend fun saveIsLogin() {
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        context.loginCheckDataStore.edit { prefs ->
            prefs[PreferenceKey.LOGIN_CHECK] = true
        }
    }

    fun getIsLogin(): Flow<Boolean> {
        return context.loginCheckDataStore.data
            .map { prefs ->
                prefs[PreferenceKey.LOGIN_CHECK] ?: false
            }
    }

    suspend fun clearDataStore() {
        context.loginCheckDataStore.edit { it.clear() }
        context.tokenDataStore.edit { it.clear() }
     }
}