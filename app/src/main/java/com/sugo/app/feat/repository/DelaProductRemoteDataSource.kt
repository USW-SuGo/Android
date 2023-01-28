package com.sugo.app.feat.repository

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit

class DelaProductRemoteDataSource (private val apiClient : SugoRetrofit):DealProductDataSource {

    override suspend fun getDealProducts(): List<DealProduct> {
        return apiClient.getMainPage(0)
    }
}