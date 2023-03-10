package com.sugo.app.feat.repository.repo.Note.Test

import com.sugo.app.feat.model.request.email
import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.login.LoginDataSource
import retrofit2.Response

class TestRemoteDataSource(private val apiService: SugoRetrofit): TestDataSource {
    override suspend fun getNoteRoom(page: Int, size: Int): Response<NoteRoom> {
        return apiService.getNoteRoom(page,size)
    }
}