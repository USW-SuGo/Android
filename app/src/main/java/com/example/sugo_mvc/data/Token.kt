package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Token(
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String
): Serializable