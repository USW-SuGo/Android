package com.sugo.app.feat.repository.repo.Note.Test

import com.sugo.app.feat.model.response.NoteRoom
import retrofit2.Response
import retrofit2.http.Query

interface TestDataSource {
//    suspend fun login(id: String, passWord: String) : Response<Unit>

    suspend fun getNoteRoom(page: Int, size: Int): Response<NoteRoom>
}