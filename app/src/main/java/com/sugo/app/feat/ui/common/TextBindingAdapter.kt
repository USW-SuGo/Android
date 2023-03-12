package com.sugo.app.feat.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sugo.app.R
import java.math.BigDecimal
import java.text.DecimalFormat


@BindingAdapter("price")
fun applyPriceFormat(view: TextView, price: Int) {
    val decimalFormat = DecimalFormat("#,###")
    view.text = view.context.getString(R.string.product_price, decimalFormat.format(price))
}

@BindingAdapter("myPageNickName")
fun applyNickNameFormat(view: TextView, nickname: String) {
   val nickNameFormat = "오늘도 수고하세요! ${nickname}님!"
    view.text =  view.context.getString(R.string.product_nickname,nickname)
}
@BindingAdapter("mannerGrade")
fun applyMannerGradeFormat(view: TextView, mannerGrade: BigDecimal) {
    val decimalFormat = DecimalFormat("#,###")
    view.text =decimalFormat.format(mannerGrade)
}
@BindingAdapter("countMannerEvaluation")
fun applyCountMannerEvaluationFormat(view: TextView,  countMannerEvaluation: Long) {
    view.text = countMannerEvaluation.toString()
}
@BindingAdapter("countTradeAttempt")
fun applyCountTradeAttemptFormat(view: TextView, countTradeAttempt: Long) {
    view.text = countTradeAttempt.toString()
}




