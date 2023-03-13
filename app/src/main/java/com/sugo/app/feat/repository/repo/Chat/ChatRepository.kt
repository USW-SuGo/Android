package com.sugo.app.feat.repository.repo.Chat

import retrofit2.Response

class ChatRepository(
    private val chatDataSource:ChatRemoteDataSource
) {

    suspend fun getNoteContent(noteId: Long, page: Int, size: Int): Response<List<Any>> {
        return chatDataSource.getNoteContent(noteId,page, size)
    }


}