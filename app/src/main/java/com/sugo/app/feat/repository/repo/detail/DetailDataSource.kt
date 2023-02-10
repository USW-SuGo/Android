package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import retrofit2.Response

interface DetailDataSource {
   suspend fun detailProduct(productPostId:Long):Response<DealProduct>
}