package com.sugo.app.feat.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sugo.app.R
import java.text.DecimalFormat

@BindingAdapter("price")
fun applyPriceFormat(view: TextView, price: Int) {
    val decimalFormat = DecimalFormat("#,###")
    view.text = view.context.getString(R.string.product_price, decimalFormat.format(price))
}


