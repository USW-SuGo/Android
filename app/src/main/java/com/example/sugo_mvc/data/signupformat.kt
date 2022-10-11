package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpFormat
    (
    @SerializedName("LoginId")  val id: String,
    val password: String,
    val email: String,
): Serializable