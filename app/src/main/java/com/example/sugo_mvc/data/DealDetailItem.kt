package com.example.sugo_mvc.data

import java.time.LocalDateTime


data class DealDetailItem(
var id: Long,
var writerId: Long,
var imageLink:String,
var contactPlace: String,
var updatedAt: LocalDateTime,
var title: String,
var content :String,
var price: Int,
var nickname: String,
var category: String,
var status :Boolean,
var userLikeStatus : Boolean
)
