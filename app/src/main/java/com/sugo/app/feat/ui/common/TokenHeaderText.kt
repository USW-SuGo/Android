package com.sugo.app.feat.ui.common

fun TokenHeadersText(originalHeaders: String?): Pair<String, String> {
    val tempHeaders = originalHeaders!!.split(", ")
    val accessToken = tempHeaders[1].replace("}", "").replace("AccessToken=", "")
    val refreshToken = tempHeaders[0].replace("{", "").replace("RefreshToken=", "")
    return Pair(accessToken, refreshToken)
}
