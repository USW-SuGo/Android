package com.sugo.app.feat.model.request

import com.google.gson.annotations.SerializedName
import kotlin.String

data class LoginFormat(
    val loginId : String,
    @SerializedName("password") val passWord : String
)
data class LoginId(
    val loginId : String
)
data class PassWord(
    @SerializedName("password") val passWord : String
)