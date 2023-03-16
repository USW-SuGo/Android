package com.sugo.app.feat.model.request

import okhttp3.MultipartBody

data class Chat(
    val noteId:Long,
    val message:String,
    val senderId:Long,
    val receiverId:Long
)
data class ChatFile(
    val noteId:Long,
    val message:String,
    val senderId:Long,
    val receiverId:MultipartBody.Part
)
