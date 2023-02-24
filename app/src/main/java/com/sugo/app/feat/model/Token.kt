package com.sugo.app.feat.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.String

data class Token(
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String
): Serializable
data class RefreshToken(
     val refreshToken: String
)