package com.example.sugo_mvc.data

import java.time.LocalDateTime

data class roomInfo2 (
    val roomId:Long,
    val creatingUserId : Long,
    val creatingUserNickname: String,
    val recentContent : String,
    val opponentUserUnreadCount : Int,
    val recentChattingDate : LocalDateTime
)