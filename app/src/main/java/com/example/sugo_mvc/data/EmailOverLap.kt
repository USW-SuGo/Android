package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EmailOverLap (
    @SerializedName("LoginId") val id: String,
        ):Serializable