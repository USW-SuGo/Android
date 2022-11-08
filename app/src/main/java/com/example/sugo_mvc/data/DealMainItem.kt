package com.example.sugo_mvc.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime


data class DealMainItem(
    var id: Long,
    var imageLink:String,
    var contactPlace: String,
    var updateAt: LocalDateTime? ,
    var title: String,
    var price: Int,
    var nickname: String,
    var category: String,
)