package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpFormat
    (
    @SerializedName("LoginId")  val loginid: String,
    val password: String,
    val email: String,
): Serializable