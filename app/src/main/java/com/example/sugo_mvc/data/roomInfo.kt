package com.example.sugo_mvc.data

import java.time.LocalDateTime

data class roomInfo (
    val noteId:Long,
    val requestUserId : Long,
    val opponentUserId : Long,
    val opponentUserNickname: String,
    val recentContent : String,
    val requestUserUnreadCount : Int,
    val recentChattingDate : LocalDateTime
)