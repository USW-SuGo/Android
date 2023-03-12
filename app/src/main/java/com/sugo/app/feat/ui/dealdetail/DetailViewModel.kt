package com.sugo.app.feat.ui.dealdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sugo.app.feat.App
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.Note
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailRepository: DetailRepository,
) : ViewModel() {
    private val _dealPrduct = MutableLiveData<DealProduct>()
    val dealProduct: LiveData<DealProduct> = _dealPrduct
    private val _dealPrduct1 = MutableLiveData<String>()
    val dealProduct1: LiveData<String> = _dealPrduct1


    fun detailProduct(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.detailProduct(productPostId)
        if(response.isSuccessful) _dealPrduct.value = response.body()
        getNoteRoom()
    }

    fun makeNote(id:Long,productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.makeNote(NoteBody(id,productPostId))
        Log.d("noteId",response.body().toString())
    }

    fun like(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.like(productPostId)
    }

    fun getNoteRoom()= viewModelScope.launch{
        val response=detailRepository.makeNoteRoom()

        val a:List<String> = response.body()!![1].toString().replace("{","").replace("[","").replace("]","").split("},")
        val test2 = mutableListOf<NoteContent>()
        val b = a[0].split(",")
        test2.add(NoteContent(b[0],b[1],b[2] ,b[3] ,b[4],b[5],b[6],b[7],b[8]))
        Log.d("response test", test2[0].toString())

    }
}