package com.example.sugo_mvc.data

import android.media.Image
import android.text.Editable
import com.google.gson.annotations.SerializedName

data class PostFormat(

    val title: String,
    val content: String,
    val price: Long,
    val contactPlace: String,
    val category: String,

    )
