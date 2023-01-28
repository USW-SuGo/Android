package com.sugo.app.feat.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.DealProductRepository
import com.sugo.app.feat.repository.DelaProductRemoteDataSource
import com.sugo.app.feat.ui.deal.DealProductViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DealProductViewModel::class.java) -> {
                val repository =
                    DealProductRepository(DelaProductRemoteDataSource(ServiceLocator.provideApiClient()))
                DealProductViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}