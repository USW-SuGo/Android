package com.sugo.app.feat.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.repository.repo.mypage.MyPageRepository
import com.sugo.app.feat.ui.common.Event
import com.sugo.app.feat.ui.common.User
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

    fun getClosePost(): Flow<PagingData<DealProduct>> {
        return repoImpl.getClosePost()
    }

    private val _openDealEvent = MutableLiveData<Event<Long>>()
    val openDealEvent: LiveData<Event<Long>> = _openDealEvent

    fun openDealDetail(productPostId: Long) {
        _openDealEvent.value = Event(productPostId)
    }

    private val _openDealEvent1 = MutableLiveData<Event<Long>>()
    val openDealEvent1: LiveData<Event<Long>> = _openDealEvent1

    fun showdialog(productPostId: Long) {
        _openDealEvent1.value = Event(productPostId)
    }

    fun upPost(productPostId: Long) = viewModelScope.launch {
        val response = myPageRepository.upPost(productPostId)
    }

    fun deletePost(productPostId: Long) = viewModelScope.launch {
        val response = myPageRepository.deletePost(productPostId).isSuccessful
    }

    fun postClose(productPostId: Long) = viewModelScope.launch {
        val response = myPageRepository.postClose(productPostId)
    }

    fun getMyPage() = viewModelScope.launch {
        val response = myPageRepository.getMyPage().body()
        if (User.loginform.value == true) _myPage.value = response!!
    }
}