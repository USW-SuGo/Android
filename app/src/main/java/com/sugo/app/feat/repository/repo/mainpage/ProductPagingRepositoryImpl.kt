package com.sugo.app.feat.repository.repo.mainpage

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.mypage.MyLikePagingDataSource
import com.sugo.app.feat.repository.repo.mypage.MyPagePagingDataSource
import kotlinx.coroutines.flow.Flow

class ProductPagingRepositoryImpl(private val apiService:SugoRetrofit): ProductPagingRepository {

    override fun getMainPage(): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            ProductPagingDataSource(apiService)
        }.flow

    override fun getSearchPage(value: String, category: String): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            SearchPagingDataSource(apiService,value,category)
        }.flow

    override fun getMyPageProduct(): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            MyPagePagingDataSource(apiService)
        }.flow
    override fun getLikeProduct(): Flow<PagingData<DealProduct>> =
        Pager(PagingConfig(10)){
            MyLikePagingDataSource(apiService)
        }.flow


}