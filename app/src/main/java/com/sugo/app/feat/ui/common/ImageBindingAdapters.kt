package com.sugo.app.feat.ui.common

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {

    /**
     * 이미지들이 배열로 오는 것이 아닌 문자열로 배열로 줌
     * **/
    val imageUrlresult = imageUrl!!.replace("[","").replace("]","").split(", ")

    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view)
            .load(imageUrlresult[0])
            .into(view)
    }
}


