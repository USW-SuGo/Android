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
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailDataSource {
   suspend fun detailProduct(productPostId:Long):Response<DealProduct>
   suspend fun makeNote(noteBody: NoteBody):Response<NoteId>

   suspend fun like(productPostId: Long):Response<Like>

   suspend fun sendChat(chat: Chat):Response<Any>

   suspend fun sendFile(noteId:MultipartBody.Part,senderId:MultipartBody.Part,receiverId:MultipartBody.Part,d:MutableList<MultipartBody.Part>):Response<Any>

}