package com.sugo.app.feat.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import kotlin.String

data class MyPage(
    @SerializedName("userId") val id: Long,
    val email: String,
    val nickname: String,
    val mannerGrade: BigDecimal,
    val countMannerEvaluation: Long,
    val countTradeAttempt: Long,
)