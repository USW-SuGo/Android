package com.sugo.app.feat.model.request

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MannerTarget(

    @SerializedName("targetUserId") val targetUserId: Long,
    @SerializedName("grade") val grade: BigDecimal
)