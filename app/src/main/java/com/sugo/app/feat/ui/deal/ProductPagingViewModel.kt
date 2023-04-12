package com.sugo.app.feat.ui.deal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductPagingViewModel(private val repoImpl: ProductPagingRepositoryImpl) : ViewModel() {
    private val _itmes = MutableStateFlow<PagingData<DealProduct>>(PagingData.empty())
    val items = _itmes.asStateFlow()

    fun getMainPage(): Flow<PagingData<DealProduct>> {
        return repoImpl.getMainPage()
    }

    fun getMyPageProduct(): Flow<PagingData<DealProduct>> {
        return repoImpl.getMyPageProduct()
    }

    private val _openDealEvent = MutableLiveData<Event<Long>>()
    val openDealEvent: LiveData<Event<Long>> = _openDealEvent

    fun openDealDetail(productPostId: Long) {
        _openDealEvent.value = Event(productPostId)
    }


}

