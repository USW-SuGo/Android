package com.sugo.app.feat.repository.repo.mainpage

import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import kotlinx.coroutines.flow.Flow

interface ProductPagingRepository {
    fun getMainPage(): Flow<PagingData<DealProduct>>
    fun getClosePost(): Flow<PagingData<DealProduct>>
    fun getSearchPage(value: String, category: String): Flow<PagingData<DealProduct>>
    fun getMyPageProduct(): Flow<PagingData<DealProduct>>
    fun getLikeProduct(): Flow<PagingData<DealProduct>>


}