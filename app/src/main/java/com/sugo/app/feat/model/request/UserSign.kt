package com.sugo.app.feat.model.request

import kotlin.String

data class UserSign(
    val loginId: String,
    val email: String,
    val password: String,
    val department : String
)
