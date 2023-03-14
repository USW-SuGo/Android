package com.sugo.app.feat.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.RequestUserId
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MessageViewModel (private val repoImpl: NotePagingRepositoryImpl) : ViewModel() {
    private val _openChatEvent = MutableLiveData<Event<List<String>>>()
    val openChatEvent: LiveData<Event<List<String>>> = _openChatEvent

    fun openChat(noteId:String,productPostId:String,creatingUserId:String,opponentUserId:String,requestUserId:String) {
        _openChatEvent.value = Event(listOf(noteId,productPostId,creatingUserId,opponentUserId,requestUserId))
    }

    fun getNoteRoom(): Flow<PagingData<NoteContent>> {
        return repoImpl.getNoteRoom()
    }
}
