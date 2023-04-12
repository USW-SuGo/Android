package com.sugo.app.feat.ui.deal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow

class SearchPagingViewModel(private val repoImpl: ProductPagingRepositoryImpl) : ViewModel() {
    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String> = _searchValue

    fun getSearchPage(value: String, category: String): Flow<PagingData<DealProduct>> {
        return repoImpl.getSearchPage(value, category)
    }

    private val _openPostEvent = MutableLiveData<Event<Long>>()
    val openPostEvent: LiveData<Event<Long>> = _openPostEvent

    fun openPostDetail() {
        _openPostEvent.value = Event(0)
    }

    fun onClickSearch(searchTxt: String) {
        _searchValue.value = searchTxt
    }
}

