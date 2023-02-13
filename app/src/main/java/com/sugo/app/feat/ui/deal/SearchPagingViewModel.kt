package com.sugo.app.feat.ui.deal

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchPagingViewModel(private val repoImpl: ProductPagingRepositoryImpl) : ViewModel() {
    private val _itmes = MutableStateFlow<PagingData<DealProduct>>(PagingData.empty())
    val items = _itmes.asStateFlow()

    private val _spinnerEntry = MutableStateFlow(emptyList<String>())
    val spinnerEntry : StateFlow<List<String>?> = _spinnerEntry
    val spinnerData = MutableStateFlow<String>("")
    private val data = listOf("전체", "서적","전자기기","생활용품","기타")
    fun setSpinnerEntry() {
        viewModelScope.launch {
            _spinnerEntry.emit(data)
        }
    }
    init {
        setSpinnerEntry()
    }

    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String> = _searchValue
    private val _categoryValue = MutableLiveData<String>()
    val categoryValue: LiveData<String> = _categoryValue

    fun getSearchPage(value: String, category: String): Flow<PagingData<DealProduct>> {
        return repoImpl.getSearchPage(value, category)
    }

    fun onClickSearch(searchTxt: String){
        _searchValue.value=searchTxt
        Log.d("TEST", "Search $searchTxt")
    }
}

