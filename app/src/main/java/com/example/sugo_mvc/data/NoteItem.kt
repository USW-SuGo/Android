package com.example.sugo_mvc.data

import java.time.LocalDateTime

data class NoteItem (
    val requestUserId:Long,
    val productPostId:Long,
    val noteContentId:Long,
    val noteFileId:Long,
    val fileSenderId:Long,
    val fileReceiverId:Long,
    val imageLink :String,
    val fileCreatedAt: LocalDateTime,
    val messageSenderId:Long,
    val messageReceiverId:Long,
    val message :String,
    val messageCreatedAt: LocalDateTime
        )