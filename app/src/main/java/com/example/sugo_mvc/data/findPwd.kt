package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName

data class findPwd (
    @SerializedName("loginId") val loginId:String,
    @SerializedName("email") val email:String
)