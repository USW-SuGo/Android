package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("loginId") val id: String,
    val email :String,
    val password :String,
    val department :String
        )

