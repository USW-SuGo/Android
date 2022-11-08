package com.example.sugo_mvc.util

import android.app.Application

class Constants {
    companion object  {
//
        const val BASE_URL: String = "https://api.sugo-diger.com"
        const val EXIST : String="/user/check-email"
        const val LOGINCHECK : String="/user/check-loginId"
        const val FindId : String ="/user/find-id"
        const val FindPwd : String ="/user/find-pw"
        const val LOGIN : String="/user/login"
        const val SIGN_UP: String = "/user/join"
        const val REQUEST_REFRESH: String = "/token"
        const val AUTH_HEADER = "Authorization"
        const val OVERLAP : String ="/user/check-id"
        const val CHANGEPWD : String = "/user/password"
        const val WITHDRAWAL : String = "/user"
        const val MANNER : String="/user/manner"
        const val MYPAGE : String="user?target={}&?page={}&size={}"
        const val USERPAGE : String="/user/?target={}&?page={}&size={}"
        const val POSTUPLOAD : String="/post"
        const val POSTGET : String = "/post/all?page=0&size=10"
        const val LECTURE_MAIN : String ="/lecture/all"
    }

}