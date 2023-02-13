package com.sugo.app.feat.ui.mypage

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sugo.app.R
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.MyPage
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.repository.repo.mypage.MyPageRepository
import com.sugo.app.feat.ui.common.Event
import com.sugo.app.feat.ui.common.TokenHeadersText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MyPageViewModel(
    val myPageRepository: MyPageRepository,
    private val repoImpl: ProductPagingRepositoryImpl,
) : ViewModel() {
    private val _myPage = MutableLiveData<MyPage>()
    val myPage: LiveData<MyPage> = _myPage

    fun getMyPageProduct(): Flow<PagingData<DealProduct>> {
        return repoImpl.getMyPageProduct()
    }

    fun getLikeProduct(): Flow<PagingData<DealProduct>> {
        return repoImpl.getLikeProduct()
    }

    private val _openDealEvent = MutableLiveData<Event<Long>>()
    val openDealEvent: LiveData<Event<Long>> = _openDealEvent


    fun openDealDetail(productPostId: Long) {
        _openDealEvent.value = Event(productPostId)
    }
    init {
        getMyPage()
    }
    private val  _openDealEvent1 = MutableLiveData<Event<Long>>()
    val openDealEvent1: LiveData<Event<Long>> = _openDealEvent1

    fun showdialog(productPostId: Long){
        _openDealEvent1.value = Event(productPostId)
    }
    fun upPost(productPostId: Long) = viewModelScope.launch {
        val response = myPageRepository.upPost(productPostId)
    }
    fun deletePost(productPostId: Long) = viewModelScope.launch {
        val response = myPageRepository.deletePost(productPostId)
    }

    private fun getMyPage() = viewModelScope.launch {
        val response = myPageRepository.getMyPage().body()
        _myPage.value = response!!
    }
}