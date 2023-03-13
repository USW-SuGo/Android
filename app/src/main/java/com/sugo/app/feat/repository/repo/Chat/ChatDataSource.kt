package com.sugo.app.feat.repository.repo.Chat

import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.NoteId
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatDataSource {
    suspend fun getNoteContent(
        noteId: Long,
        page: Int,
        size: Int
    ): Response<List<Any>>
}