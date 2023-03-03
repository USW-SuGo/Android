package com.sugo.app.feat.model


import java.time.LocalDateTime

import com.google.gson.annotations.SerializedName
import kotlin.String

data class DealProduct(
    @SerializedName("productPostId")val id: Long,
    val imageLink: String,
    val contactPlace: String,
    val updatedAt: LocalDateTime,
    val title: String,
    val price: Int,
    val nickname: String,
    val category: String,
    val content: String,
    val status :Boolean,
)
data class ProductPostId(
    @SerializedName("productPostId") val id: Long
)
data class ImageLink(
    val imageLink: String,
)


