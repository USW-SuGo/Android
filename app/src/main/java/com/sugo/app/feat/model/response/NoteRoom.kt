package com.sugo.app.feat.model.response

import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import java.time.LocalDateTime


data class Note(
    val note:NoteRoom
)
data class NoteRoom(
    @SerializedName("requestUserId") val requestUserId: Long,
    val notes:List<NoteContent>
)
data class RequestUserId(
    val requestUserId: Long
)
//data class NoteContent(
//    val imageLink: String,
//    val noteId: Long,
//    val productPostId: Long,
//    val creatingUserId: Long,
//    val opponentUserId: Long,
//    val opponentUserNickname: String,
//    val recentContent: String,
//    val requestUserUnreadCount: Int,
//    val recentChattingDate: String
//)

data class NoteContent(
    val imageLink: String,
    val noteId: String,
    val productPostId: String,
    val creatingUserId: String,
    val opponentUserId:String,
    val opponentUserNickname: String,
    val recentContent: String,
    val requestUserUnreadCount:String,
    val recentChattingDate: String
)



