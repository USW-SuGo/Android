package com.sugo.app.feat.ui.dealdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailRepository: DetailRepository,
) : ViewModel() {
    private val _dealPrduct = MutableLiveData<DealProduct>()
    val dealProduct: LiveData<DealProduct> = _dealPrduct

    //    init {
//        detailProduct(180)
//    }
//    init {
//        invokeException()
//    }
//
//    private fun invokeException() {
//        viewModelScope.launch(coroutineExceptionHandler) {
//            throw IllegalStateException()
//        }
//    }

    fun detailProduct(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.detailProduct(productPostId)
        if(response.isSuccessful) _dealPrduct.value = response.body()
    }
}