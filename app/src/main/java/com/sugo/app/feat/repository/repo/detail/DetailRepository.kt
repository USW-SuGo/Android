package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.Chat
import com.sugo.app.feat.model.request.ChatFile
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.Like
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.model.response.NoteRoom
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class DetailRepository(
    private val detailRemoteDataSource:DetailRemoteDataSource
) {
    suspend fun detailProduct(productPostId: Long):Response<DealProduct>{
        return detailRemoteDataSource.detailProduct(productPostId)
    }
    suspend fun makeNote(noteBody: NoteBody):Response<NoteId>{
        return detailRemoteDataSource.makeNote(noteBody)
    }

    suspend fun like(productPostId: Long):Response<Like>{
        return detailRemoteDataSource.like(productPostId)
    }
    suspend  fun sendChat(chat: Chat):Response<Any>{
        return detailRemoteDataSource.sendChat(chat)
    }

    suspend  fun sendFile(noteId:MultipartBody.Part,senderId:MultipartBody.Part,receiverId:MultipartBody.Part,d:MutableList<MultipartBody.Part>):Response<Any>{
        return detailRemoteDataSource.sendFile(noteId,senderId,receiverId,d)
    }
}