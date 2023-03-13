package com.sugo.app.feat.repository.repo.Chat

import androidx.paging.PagingData
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.model.response.NoteRoom
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatDataSource {
    fun getNoteRoom(noteId:Long): Flow<PagingData<ChatRoom>>

}