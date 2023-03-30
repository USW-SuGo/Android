package com.sugo.app.feat.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugo.app.feat.ui.common.Event

class UploadViewModel:ViewModel() {
    private val _openContactEvent = MutableLiveData<Event<Long>>()
    val openContactEvent: LiveData<Event<Long>> = _openContactEvent

    fun openContact() {
        _openContactEvent.value = Event(0)
    }
}