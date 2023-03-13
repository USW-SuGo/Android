package com.sugo.app.feat.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.RequestUserId
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow

class MessageViewModel (private val repoImpl: NotePagingRepositoryImpl) : ViewModel() {
    private val _openChatEvent = MutableLiveData<Event<String>>()
    val openChatEvent: LiveData<Event<String>> = _openChatEvent

    fun openChat(requestUserId: String) {
        _openChatEvent.value = Event(requestUserId)
    }
    fun getNoteRoom(): Flow<PagingData<NoteContent>> {
        return repoImpl.getNoteRoom()
    }
}
