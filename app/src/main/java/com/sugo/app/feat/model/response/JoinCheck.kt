package com.sugo.app.feat.model.response

data class JoinCheck(
    val Success : Boolean,
    val id :Long,
    val error : String,
    val message : String
)
