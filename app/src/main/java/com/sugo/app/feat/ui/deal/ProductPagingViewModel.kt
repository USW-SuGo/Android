package com.sugo.app.feat.ui.deal

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.maindealpage.ProductPagingRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductPagingViewModel(private val repoImpl: ProductPagingRepositoryImpl):ViewModel() {
    private val _itmes = MutableStateFlow<PagingData<DealProduct>>(PagingData.empty())
    val items = _itmes.asStateFlow()

    fun getMainPage(): Flow<PagingData<DealProduct>> {
        return repoImpl.getMainPage()
    }

}