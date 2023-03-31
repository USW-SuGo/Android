package com.sugo.app.feat.ui.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UploadViewModel : ViewModel() {

    private val _openContactEvent = MutableLiveData<Event<Long>>()
    val openContactEvent: LiveData<Event<Long>> = _openContactEvent
    private val _place = MutableLiveData<String>()
    val place: LiveData<String> = _place

    private val _spinnerEntry = MutableStateFlow(emptyList<String>())
    val spinnerEntry: StateFlow<List<String>?> = _spinnerEntry
    val spinnerData = MutableStateFlow<String>("")

    fun openContact() {
        _openContactEvent.value = Event(0)
    }

    fun setSpinnerEntry(Entry: List<String>) {
        viewModelScope.launch {
            _spinnerEntry.emit(Entry)
        }
    }

    fun setPlace(getDepartment: String) {
        _place.value = getDepartment
    }


}