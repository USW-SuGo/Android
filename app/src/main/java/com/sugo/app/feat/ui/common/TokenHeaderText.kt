package com.sugo.app.feat.ui.common

import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent

fun TokenHeadersText(originalHeaders: String?): Pair<String, String> {
    val tempHeaders = originalHeaders!!.split(", ")
    val accessToken = tempHeaders[1].replace("}", "").replace("AccessToken=", "")
    val refreshToken = tempHeaders[0].replace("{", "").replace("RefreshToken=", "")
    return Pair(accessToken, refreshToken)
}
fun MessageList(noteContent:List<String>,test:String):List<NoteContent>{
    val test2 = mutableListOf<NoteContent>()
    for (i in 0..noteContent.size-1){
        val b = noteContent[i].split(",")
        test2.add(
            NoteContent(b[0].replace("imageLink=","").replace(" ",""),b[1].replace(" ","").replace("noteId=",""),b[2].replace(" ","").replace("productPostId=","") ,b[3].replace("creatingUserId=","") ,b[4].replace("opponentUserId=",""),b[5].replace("opponentUserNickname=",""),
                b[6].replace("recentContent=",""),b[7].replace("requestUserUnreadCount=",""),b[8].replace("recentChattingDate=",""),test)
        )
    }
    return test2
}

fun ChatList(noteContent:List<String>,test:String):List<ChatRoom>{
    val test2 = mutableListOf<ChatRoom>()
    for (i in 0..noteContent.size-1){
        val b = noteContent[i].split(",")
        test2.add(
                ChatRoom(b[0],b[1],b[2].replace("message=",""),b[3].replace("imageLink=","").replace(" ",""),b[4].replace("senderId=",""),b[5].replace("receiverId=",""),b[6].replace("createdAt=",""),test.replace("{requestUserId=",""))
      )
    }
    return test2
}
fun MessageRoom(noteRoom:String):List<String>{
    return noteRoom.replace("{","").replace("[","").replace("]","").split("},")
}
fun chatLong(id:String):String{
    return id.replace(" ","").substringBefore(".")
}



