package com.sugo.app.feat.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.Success
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.model.response.User
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.repository.repo.user.UserRepository
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(
    val userRepository: UserRepository,

) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    fun getUser(userId: Long) = viewModelScope.launch {
        val response = userRepository.getUser(userId)
        if (response.isSuccessful) {
            _user.value = response.body()
        }
    }

    fun setManner(mannerTarget: MannerTarget) = viewModelScope.launch {
        val response = userRepository.setManner(mannerTarget)
    }
    fun getUserProdcut(userId: Long): Flow<PagingData<DealProduct>> {
        return userRepository.getUserPageProduct(userId)
    }

    fun getUserCompleteProduct(userId: Long): Flow<PagingData<DealProduct>> {
        return userRepository.getUserCompletePageProduct(userId)
    }

}