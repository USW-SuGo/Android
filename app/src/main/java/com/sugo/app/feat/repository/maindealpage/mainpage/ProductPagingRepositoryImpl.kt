package com.sugo.app.feat.repository.maindealpage.mainpage

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit
import kotlinx.coroutines.flow.Flow

class ProductPagingRepositoryImpl(private val apiService:SugoRetrofit): ProductPagingRepository {

    override fun getMainPage(): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            ProductPagingDataSource(apiService)
        }.flow
}