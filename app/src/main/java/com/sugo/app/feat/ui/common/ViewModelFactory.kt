package com.sugo.app.feat.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager
import com.sugo.app.feat.repository.repo.detail.DetailRemoteDataSource
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import com.sugo.app.feat.repository.repo.login.LoginRemoteDataSource
import com.sugo.app.feat.repository.repo.login.LoginRepository
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.ui.deal.ProductPagingViewModel
import com.sugo.app.feat.ui.deal.SearchPagingViewModel
import com.sugo.app.feat.ui.dealdetail.DetailViewModel
import com.sugo.app.feat.ui.login.LoginViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductPagingViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideApiClient()))
                ProductPagingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchPagingViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideTokenApiClient()))
                SearchPagingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                val repository =
                    LoginRepository(LoginRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                LoginViewModel(repository, TokenPreferenceManager(context)) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->{
                val repository =
                    DetailRepository(DetailRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                DetailViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}