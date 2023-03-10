package com.sugo.app.feat.repository.repo.Note.Test

import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.repository.repo.login.LoginRemoteDataSource
import retrofit2.Response
import retrofit2.http.Query

class TestRepository  ( private val testDataSource: TestRemoteDataSource
)  {
    suspend fun getNoteRoom(page:Int,size:Int): Response<NoteRoom>{
        return testDataSource.getNoteRoom(page,size)
    }
}