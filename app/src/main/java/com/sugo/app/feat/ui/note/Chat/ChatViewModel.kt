package com.sugo.app.feat.ui.note.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.repository.repo.Chat.ChatRepository
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import com.sugo.app.feat.ui.common.Event
import kotlinx.coroutines.flow.Flow

class ChatViewModel  (private val chatRepository: ChatRepository) : ViewModel() {

    fun getChatRoom(): Flow<PagingData<ChatRoom>> {
        return chatRepository.getNoteRoom()
    }
}