package com.sugo.app.feat.repository.repo.Chat

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.Note.NoteDataSource
import com.sugo.app.feat.repository.repo.Note.NotePagingDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ChatRepository(private val apiService: SugoRetrofit) : ChatDataSource {

    override fun getNoteRoom(noteId: Long): Flow<PagingData<ChatRoom>> =
        Pager(PagingConfig(10)) {
            ChatRemoteDataSource(apiService, noteId)
        }.flow


}