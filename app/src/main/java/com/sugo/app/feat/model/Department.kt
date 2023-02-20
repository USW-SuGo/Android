package com.sugo.app.feat.model

import com.google.gson.annotations.SerializedName

data class Department(
    @SerializedName("departments") val departments: ArrayList<String>
)
