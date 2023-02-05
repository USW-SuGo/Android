package com.sugo.app.feat.repository.repo.Token

interface TokenLocalDataSource {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
    fun getRefreshToken(): String?
    fun saveRefreshToken(token: String?)
}