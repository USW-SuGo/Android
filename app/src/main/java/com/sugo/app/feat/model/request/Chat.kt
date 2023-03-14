package com.sugo.app.feat.model.request

data class Chat(
    val noteId:Long,
    val message:String,
    val senderId:Long,
    val receiverId:Long
)
