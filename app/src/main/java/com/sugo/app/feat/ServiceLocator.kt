package com.sugo.app.feat

import com.sugo.app.feat.network.SugoRetrofit

object ServiceLocator {

    private var apiClient: SugoRetrofit? = null

    fun provideApiClient(): SugoRetrofit {
        return apiClient ?: kotlin.run {
            SugoRetrofit.create().also {
                apiClient = it
            }
        }
    }
}