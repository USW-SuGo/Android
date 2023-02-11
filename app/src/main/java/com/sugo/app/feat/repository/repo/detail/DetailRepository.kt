package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import retrofit2.Response

class DetailRepository(
    private val detailRemoteDataSource:DetailRemoteDataSource
) {
    suspend fun detailProduct(productPostId: Long):Response<DealProduct>{
        return detailRemoteDataSource.detailProduct(productPostId)
    }
}