package com.sugo.app.feat.ui.deal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.Event
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.DealProductRepository
import kotlinx.coroutines.launch

class DealProductViewModel(
    private val dealProductRepository: DealProductRepository
): ViewModel() {
    private val _itmes = MutableLiveData<List<DealProduct>>()
    val items : LiveData<List<DealProduct>> = _itmes

    init {
        loadCategory()
    }

    //ui thread 실행하면 안되게 하자
    private fun loadCategory(){
        viewModelScope.launch {
            val dealProducts = dealProductRepository.getDealProducts()
            _itmes.value = dealProducts
        }
    }
}