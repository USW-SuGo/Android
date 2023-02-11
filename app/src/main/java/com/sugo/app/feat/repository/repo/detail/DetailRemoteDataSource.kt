package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class DetailRemoteDataSource(private val apiService:SugoRetrofit):DetailDataSource {
    override suspend fun detailProduct(productPostId: Long): Response<DealProduct> {
        return apiService.detailProduct(productPostId)
    }
}