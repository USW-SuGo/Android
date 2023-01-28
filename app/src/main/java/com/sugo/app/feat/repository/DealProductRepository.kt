package com.sugo.app.feat.repository

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit

class DealProductRepository(private val remoteDataSource: DelaProductRemoteDataSource ){

    suspend fun getDealProducts():List<DealProduct>{
        return remoteDataSource.getCategories()
    }
}