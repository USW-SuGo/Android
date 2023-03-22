package com.sugo.app.feat.model.request

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.Part

data class Chat(
    val noteId:Long,
    val message:String,
    val senderId:Long,
    val receiverId:Long
)

data class ChatFile(
    val noteId: Long,
    val receiverId: Long,
)
