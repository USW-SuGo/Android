package com.example.sugo_mvc.data

import android.provider.ContactsContract
import java.math.BigDecimal

data class Userpage (
    val userId : Long,
    val email : String,
    val nickname: String,
    val mannerGrade : BigDecimal,
    val countMannerEvaluation : Int,
    val countTradeAttempt:Int,
    val myPosting : List<DealMainItem>,
    val likePosting : List<DealMainItem>
        )