package com.sugo.app.feat.common

import com.sugo.app.feat.model.response.MyPage

object NetWork {
    const val BASE_URL = "https://api.sugo-diger.com"
    const val MainPage = "/post/all"
    const val Login = "/user/login"
    const val TokenRefreshAPI = "/token"
    const val FindId= "/user/find-id"
    const val  Search = "/post/search"
    const val DetailProduct ="/post/{productPostId}"
    const val MyPageProduct ="/post/my-post"
    const val LikePost = "/like-post"
    const val MyPage= "/user"
    const val UpPost="/post/up-post"
    const val ClosePost="/post/close"
    const val getClosePost = "/post/close-post"
    const val CheckId="/user/check-loginId"
    const val CheckEmail="/user/check-email"
    const val Join = "/user/join"
    const val AuthPayLoad="/user/auth"
    const val Note = "/note"
    const val POSTUPLOAD = "/post"
    const val LIKE = "/like-post"
    const val NOTEROOM = "/note/list"
    const val CHATROOM = "/note-content/{noteId}"
    const val SENDCHAT = "/note-content/text"
    const val SEND_FILE="/note-content/file"
    const val FCMTOKEN = "/user/fcm"

}