package com.sugo.app.feat.model.response

import com.sugo.app.feat.model.ProductPostId

data class ChatRoom(
    val productPostId: String,
    val noteContentId: String,
    val message: String,
    val imageLink: String,
    val senderId: String,
    val receiverId: String,
    val createdAt: String,
)