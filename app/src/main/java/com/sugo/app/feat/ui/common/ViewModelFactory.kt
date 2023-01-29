package com.sugo.app.feat.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.repository.maindealpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.ui.deal.paging.ProductPagingViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductPagingViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideApiClient()))
                ProductPagingViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}