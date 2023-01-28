package com.sugo.app.feat.model


import java.time.LocalDateTime

import com.google.gson.annotations.SerializedName
data class DealProduct(
    @SerializedName("id")val productPostId: Long,
    val imageLink:String,
    val contactPlace: String,
    val updatedAt: LocalDateTime,
    val title: String,
    val price: Int,
    val nickname: String,
    val category: String,
    val status :Boolean,
    val userLikeStatus :Boolean
)