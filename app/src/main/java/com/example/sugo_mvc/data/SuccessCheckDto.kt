package com.example.sugo_mvc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SuccessCheckDto(
    @SerializedName("Success") val success: Boolean
) : Serializable