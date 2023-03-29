package com.sugo.app.feat.network

import android.util.Log
import com.sugo.app.feat.App
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.model.Token
import com.sugo.app.feat.ui.common.TokenHeadersText
import com.sugo.app.feat.ui.common.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import retrofit2.Response

/**
 * 1. 일단 메인엑티비티에서 엑세스 토큰을 확인하고 유효하면 토큰유지
 * 2. 로그인 상태 true
 * 3. 만약 엑세스 토큰 유효하지 않으면 리프레시 토큰으로 재발급
 * 4. 만약 리프레시토큰도 재발급이 되지않는다면
 * 5. 로그인 상태 false 로 변경하여 라이브데이터 옵저버를 통해 로그인 기능으로 이동
 *
 * 로그인이 아예 안돼있는 상태에서 클릭시
 * 로그인 상태는 false 임으로 위의 행동 반복할 필요가 없음
 * 엑세스토큰이 필요한 api를 호출할 경우 ex)자세히보기 마이페이지 등
 * 그렇다면 토큰 에러가 떨어질 것임
 * 그렇다면 5번을 반복??
 *
 * **/
class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val refreshTokenRequest = App.prefs.getRefreshToken() ?: ""
        val response1 =
            SugoRetrofit.create().refreshAccessToken(refreshTokenRequest).execute()
        return if (handleResponse(response1)) {
            response.request
                .newBuilder()
                .removeHeader("Authorization")
                .header("Authorization",App.prefs.getAccessToken() ?: "")
                .build()
        } else {
            null
        }
    }

    private fun handleResponse(
        response: Response<Token>
    ) =
        if (response.isSuccessful && response.body() != null) {
            Log.d("TokenAuthenticator1", response.headers().toString())
            val a =response.headers().get("authorization")
            val (accesstoken,refreshtoken) = TokenHeadersText(a)
            App.prefs.saveAccessToken( accesstoken)
            App.prefs.saveRefreshToken( refreshtoken)
            CoroutineScope(Dispatchers.Main).launch {
                User.loginform.value = true
            }
            true
        } else {
            Log.d("실패실패", "실패했어요 토큰")
            CoroutineScope(Dispatchers.Main).launch {
                User.loginform.value = false
            }
            false
        }
}