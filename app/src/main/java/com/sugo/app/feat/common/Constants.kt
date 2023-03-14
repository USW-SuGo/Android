package com.sugo.app.feat.common

import com.sugo.app.feat.model.response.MyPage

object NetWork {
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
    const val CheckId="/user/check-loginId"
    const val CheckEmail="/user/check-email"
    const val Join = "/user/join"
    const val AuthPayLoad="/user/auth"
    const val Note = "/note"
    const val POSTUPLOAD = "/post"
    const val LIKE = "/like-post"
    const val NOTEROOM = "/note/list"
    const val CHATROOM = "/note-content/{noteId}"

}