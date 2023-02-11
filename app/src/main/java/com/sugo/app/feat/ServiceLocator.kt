package com.sugo.app.feat

import android.content.SharedPreferences
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

    fun provideTokenApiClient(): SugoRetrofit {
        return apiClient ?: kotlin.run {
            SugoRetrofit.tokenCreate().also {
                apiClient = it
            }
        }
    }
}