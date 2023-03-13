package com.sugo.app.feat.ui.chat.MessageList

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import kotlinx.coroutines.flow.Flow

class MessageViewModel (private val repoImpl: NotePagingRepositoryImpl) : ViewModel() {
    fun getNoteRoom(): Flow<PagingData<NoteContent>> {
        return repoImpl.getNoteRoom()
    }
}
