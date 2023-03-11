package com.sugo.app.feat.model.response

import com.sugo.app.feat.model.ProductPostId
import java.time.LocalDateTime

data class NoteRoom(
    val requestUserId: Long,
    val noteContent: List<NoteContent>
)

data class NoteContent(
    val imageLink: String,
    val noteId: Long,
    val productPostId: Long,
    val creatingUserId: Long,
    val opponentUserId: Long,
    val opponentUserNickname: String,
    val recentContent: String,
    val requestUserUnreadCount: Int,
    val recentChattingDate: LocalDateTime
)
