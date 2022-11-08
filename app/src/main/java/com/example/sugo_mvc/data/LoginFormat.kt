package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginFormat(
    @SerializedName("loginId")  val loginId: String,
    @SerializedName("password") val password: String
): Serializable