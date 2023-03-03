package com.sugo.app.feat.ui.dealdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailRepository: DetailRepository,
) : ViewModel() {
    private val _dealPrduct = MutableLiveData<DealProduct>()
    val dealProduct: LiveData<DealProduct> = _dealPrduct



    fun detailProduct(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.detailProduct(productPostId)
        if(response.isSuccessful) _dealPrduct.value = response.body()
    }

    fun makeNote(id:Long,productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.makeNote(NoteBody(id,productPostId))
        Log.d("noteId",response.body().toString())
    }

    fun like(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.like(productPostId)
    }
}