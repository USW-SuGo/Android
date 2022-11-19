package com.example.sugo_mvc.data

import java.time.LocalDateTime

data class roomInfo (
    val roomId:Long,
    val opponentUserId : Long,
    val opponentUserNickname: String,
    val recentContent : String,
    val opponentUserIUsernreadCount : Int,
    val recentChattingDate : String
)