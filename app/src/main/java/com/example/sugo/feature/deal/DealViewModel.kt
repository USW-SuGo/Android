package com.example.sugo.feature.deal

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sugo.feature.data.DealData
import com.example.sugo.feature.repository.deal.DealRepository

class DealViewModel(
//    private val dealrepository:DealRepository
) : ViewModel() {
    var dealData : MutableLiveData<ArrayList<DealData>> = MutableLiveData<ArrayList<DealData>>()

    init{
        var UserData = ArrayList<DealData>()
        UserData.add(DealData("asd","asd","asd","asd"))
        UserData.add(DealData("asd2","asd","asd","asd"))
        UserData.add(DealData("asd3","asd","asd","asd"))

        dealData.postValue(UserData)
    }
}
