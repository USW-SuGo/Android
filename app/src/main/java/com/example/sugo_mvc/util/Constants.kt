package com.example.sugo_mvc.util

class Constants {
    companion object  {

        const val BASE_URL: String = " https://api.sugo-diger.com"
        const val EXIST : String="/user/check-email"
        const val LOGINCHECK : String="/user/check-loginId"
        const val EmailSend : String ="/user/send-authorization-email"


        const val LOGIN : String="/user/login"
        const val SIGN_UP: String = "/user/join"
        const val REQUEST_REFRESH: String = "/token"
        const val AUTH_HEADER = "Authorization"
        const val OVERLAP : String ="/user/check-id"

    }

}