package com.sugo.app.feat.model.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class User(
    @SerializedName("userId") val userId: Long,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("mannerGrade") val mannerGrade: BigDecimal,
    @SerializedName("countMannerEvaluation")val countMannerEvaluation: Long,
    @SerializedName("countTradeAttempt") val countTradeAttempt: Long,
)

