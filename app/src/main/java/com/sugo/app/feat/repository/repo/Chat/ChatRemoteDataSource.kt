package com.sugo.app.feat.repository.repo.Chat

import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.detail.DetailDataSource
import retrofit2.Response

class ChatRemoteDataSource(private val apiService: SugoRetrofit): ChatDataSource {


    override suspend fun getNoteContent(noteId: Long, page: Int, size: Int): Response<List<Any>> {
       return apiService.getNoteContent(noteId,page,size)
    }
}