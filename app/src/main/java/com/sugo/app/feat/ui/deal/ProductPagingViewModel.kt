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

class ProductPagingViewModel(private val repoImpl: ProductPagingRepositoryImpl):ViewModel() {
    private val _itmes = MutableStateFlow<PagingData<DealProduct>>(PagingData.empty())
    val items = _itmes.asStateFlow()

    fun getMainPage(): Flow<PagingData<DealProduct>> {
        return repoImpl.getMainPage()
    }

    private val _openDealEvent = MutableLiveData<Event<DealProduct>>()
    val openDealEvent: LiveData<Event<DealProduct>> = _openDealEvent

    fun openDealDetail(productPostId:DealProduct){
        _openDealEvent.value = Event(productPostId)
    }
}